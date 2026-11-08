package br.com.trabalho.carrinhodecompras.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import br.com.trabalho.carrinhodecompras.exception.FinalizacaoDeCompraException;
import br.com.trabalho.carrinhodecompras.model.Carrinho;
import br.com.trabalho.carrinhodecompras.model.Compra;
import br.com.trabalho.carrinhodecompras.model.ComprovanteDePagamento;
import br.com.trabalho.carrinhodecompras.model.ItemComprado;
import br.com.trabalho.carrinhodecompras.model.Produto;
import br.com.trabalho.carrinhodecompras.model.Usuario;
import br.com.trabalho.carrinhodecompras.utils.Utils;

public class ControllerCompra {
    private final ControllerPagamento controllerPagamento = new ControllerPagamento();
    private final Utils utils = new Utils();

    /** @return true se a compra foi concluída; false se não foi (e nada foi alterado). */
    public boolean finalizarCompra (Usuario usuario) {
        try {
            List<Carrinho> carrinho = Carrinho.getListaDeProdutosNoCarrinho();
            BigDecimal total = validarCarrinho(carrinho);

            Optional<ComprovanteDePagamento> comprovante = controllerPagamento.realizarPagamento(total);

            if (comprovante.isEmpty()) {
                return false;
            }

            Compra compra = Compra.criar(usuario.getNome(), copiarItens(carrinho), comprovante.get());

            baixarEstoque(compra.getItens());
            Compra.adicionarCompraAoHistorico(compra);
            Carrinho.limpar();

            imprimirResumo(compra);
            return true;
        } catch (FinalizacaoDeCompraException e) {
            utils.imprimirMensagem("\n" + e.getMessage() + "\n");
            return false;
        }
    }

    /** Resumo final: produtos, quantidades, preços unitários, subtotal, desconto e valor final. */
    private void imprimirResumo (Compra compra) {
        utils.limparTerminal();
        utils.imprimirMensagem("Compra finalizada com sucesso! Compra nº " + compra.getId() + "\n\n");

        for (ItemComprado item : compra.getItens()) {
            utils.imprimirMensagem(item.quantidade() + "x " + item.nome() + " - " + utils.formatarMoeda(item.precoUnitario())
                    + " cada = " + utils.formatarMoeda(item.subtotal()) + "\n");
        }

        utils.imprimirMensagem("\nSubtotal: " + utils.formatarMoeda(compra.getValorDaCompra()) + "\n");
        utils.imprimirMensagem("Desconto (" + compra.getFormaDePagamento() + "): "
                + utils.formatarMoeda(compra.getValorDaCompra().subtract(compra.getValorPago())) + "\n");
        utils.imprimirMensagem("Valor final: " + utils.formatarMoeda(compra.getValorPago()) + "\n");
    }

    /** Confere o carrinho inteiro antes de qualquer alteração e devolve o total. */
    private BigDecimal validarCarrinho (List<Carrinho> carrinho) {
        if (carrinho.isEmpty()) {
            throw new FinalizacaoDeCompraException("o carrinho está vazio.");
        }

        for (Carrinho item : carrinho) {
            Produto produto = Produto.buscarPorId(Produto.getProdutos(), item.getIdProduto());

            if (produto == null) {
                throw new FinalizacaoDeCompraException("o produto \"" + item.getNome() + "\" não existe mais. Remova-o do carrinho.");
            }

            if (produto.getQuantidade() == 0) {
                throw new FinalizacaoDeCompraException("o produto \"" + item.getNome() + "\" está esgotado. Remova-o do carrinho.");
            }

            if (item.getQuantidade() <= 0 || item.getQuantidade() > produto.getQuantidade()) {
                throw new FinalizacaoDeCompraException("estoque insuficiente de \"" + item.getNome()
                        + "\" (pedido: " + item.getQuantidade() + ", disponível: " + produto.getQuantidade() + "). Ajuste o carrinho.");
            }
        }

        return Carrinho.calcularSubtotal();
    }

    private List<ItemComprado> copiarItens (List<Carrinho> carrinho) {
        List<ItemComprado> itens = new ArrayList<>();

        for (Carrinho item : carrinho) {
            itens.add(new ItemComprado(item.getIdProduto(), item.getNome(), item.getPreco(), item.getQuantidade()));
        }

        return itens;
    }

    /** Tudo ou nada: se um produto falhar, os que já tiveram baixa voltam ao estoque anterior. */
    private void baixarEstoque (List<ItemComprado> itens) {
        List<ItemComprado> jaBaixados = new ArrayList<>();

        try {
            for (ItemComprado item : itens) {
                Produto produto = Produto.buscarPorId(Produto.getProdutos(), item.idProduto());

                produto.setQuantidade(produto.getQuantidade() - item.quantidade());
                jaBaixados.add(item);
            }
        } catch (RuntimeException e) {
            for (ItemComprado item : jaBaixados) {
                Produto produto = Produto.buscarPorId(Produto.getProdutos(), item.idProduto());

                produto.setQuantidade(produto.getQuantidade() + item.quantidade());
            }

            throw new FinalizacaoDeCompraException("falha ao atualizar o estoque (nada foi alterado).");
        }
    }
}
