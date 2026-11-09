package br.com.trabalho.carrinhodecompras.controller;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import br.com.trabalho.carrinhodecompras.io.MenuView;
import br.com.trabalho.carrinhodecompras.io.UsuarioView;
import br.com.trabalho.carrinhodecompras.io.LeitorDeOpcao;
import br.com.trabalho.carrinhodecompras.io.LeitorDeCarrinho;
import br.com.trabalho.carrinhodecompras.model.Carrinho;
import br.com.trabalho.carrinhodecompras.model.Compra;
import br.com.trabalho.carrinhodecompras.model.Produto;
import br.com.trabalho.carrinhodecompras.model.Usuario;
import br.com.trabalho.carrinhodecompras.exception.LeituraInvalidaException;
import br.com.trabalho.carrinhodecompras.utils.Utils;

public class ControllerUsuario {
    private final UsuarioView usuarioView = new UsuarioView();
    private final MenuView menuView = new MenuView();
    private final ControllerCompra controllerCompra = new ControllerCompra();
    private final LeitorDeOpcao leitorDeOpcao = new LeitorDeOpcao();
    private final LeitorDeCarrinho leitorDeCarrinho = new LeitorDeCarrinho();
    private final Utils utils = new Utils();

    public void listarProdutos () {
        utils.limparTerminal();

        if (Produto.getProdutos().isEmpty()) {
            utils.imprimirMensagem("Nenhum produto disponível no momento.\n");
        }

        for (Produto produto : Produto.getProdutos()) {
            usuarioView.imprimirProduto(produto);
        }

        utils.pausar();
    }

    /** Lê o id, mostra o produto, lê a quantidade e só adiciona se o cliente confirmar. */
    private void adicionarProdutoAoCarrinho () {
        try {
            int idProduto = leitorDeCarrinho.lerId();
            Produto produto = Produto.buscarPorId(Produto.getProdutos(), idProduto);

            if (produto == null) {
                utils.imprimirMensagem("\nO produto informado não existe.");
                return;
            }

            if (Carrinho.buscarPorId(Carrinho.getListaDeProdutosNoCarrinho(), idProduto) != null) {
                utils.imprimirMensagem("\nO produto já foi adicionado ao carrinho. Para mudar a quantidade, use a opção Alterar Quantidade.");
                return;
            }

            utils.imprimirMensagem("\n");
            usuarioView.imprimirProduto(produto);

            if (produto.getQuantidade() == 0) {
                utils.imprimirMensagem("Este produto está esgotado.");
                return;
            }

            int quantidade = leitorDeCarrinho.lerQuantidade();

            if (quantidade > produto.getQuantidade()) {
                throw new LeituraInvalidaException(String.valueOf(quantidade), "quantidade", "o valor inserido é maior do que o contido no estoque.");
            }

            if (!confirmarAdicao(produto, quantidade)) {
                utils.imprimirMensagem("\nAdição cancelada.");
                return;
            }

            int id = Carrinho.buscarUltimoId(Carrinho.getListaDeProdutosNoCarrinho()) + 1;
            Carrinho.adicionarProdutoAOCarrinho(new Carrinho(id, idProduto, produto.getNome(), produto.getPreco(), quantidade));
            utils.imprimirMensagem("\nProduto adicionado com sucesso!");
        } catch (LeituraInvalidaException e) {
            utils.imprimirMensagem("\n" + e.getMessage() + "\n");
        }
    }

    private boolean confirmarAdicao (Produto produto, int quantidade) {
        while (true) {
            utils.imprimirMensagem("\nDeseja adicionar " + quantidade + " unidade(s) de \"" + produto.getNome() + "\" ao carrinho?\n[1] Sim.\n[2] Não.\n\n");

            int opcao = leitorDeOpcao.lerOpcao();

            if (opcao == 1) return true;
            if (opcao == 2) return false;

            utils.imprimirMensagem("Insira 1 para confirmar ou 2 para cancelar.\n\n");
        }
    }

    public void gerenciarCarrinho (Usuario usuario) {
        int opcao;
        boolean looping = true;

        while (looping) {
            utils.limparTerminal();

            for (Carrinho produtoNoCarrinho : Carrinho.getListaDeProdutosNoCarrinho()) {
                int id = produtoNoCarrinho.getId();
                String nome = produtoNoCarrinho.getNome();
                BigDecimal preco = produtoNoCarrinho.getPreco();
                int quantidade = produtoNoCarrinho.getQuantidade();

                usuarioView.imprimirItemDoCarrinho(id, nome, preco, quantidade);

                Produto produtoNoEstoque = Produto.buscarPorId(Produto.getProdutos(), produtoNoCarrinho.getIdProduto());

                if (produtoNoEstoque == null) {
                    utils.imprimirMensagem("ATENÇÃO: este produto não existe mais. Retire-o do carrinho.\n\n");
                } else if (produtoNoEstoque.getQuantidade() == 0) {
                    utils.imprimirMensagem("ATENÇÃO: este produto está esgotado. Retire-o do carrinho.\n\n");
                }
            }

            if (Carrinho.getListaDeProdutosNoCarrinho().isEmpty()) {
                utils.imprimirMensagem("Seu carrinho está vazio.\n\n");
            }

            utils.imprimirMensagem("Subtotal: " + utils.formatarMoeda(Carrinho.calcularSubtotal()) + "\n\n");
            menuView.imprimirMenuCarrinho();

            opcao = leitorDeOpcao.lerOpcao();

            switch (opcao) {
                case 1:
                    adicionarProdutoAoCarrinho();
                    utils.pausar();
                    break;

                case 2:
                    controllerCompra.finalizarCompra(usuario);
                    utils.pausar();
                    break;

                case 3:
                    int id = leitorDeCarrinho.lerId();
                    boolean produtoEncontrado = false;

                    for (Carrinho produtoNoCarrinho : Carrinho.getListaDeProdutosNoCarrinho()) {
                        if (produtoNoCarrinho.getId() == id) {
                            produtoEncontrado = true;
                            break;
                        }
                    }

                    if (produtoEncontrado) {
                        utils.imprimirMensagem("\nProduto excluido do carrinho.");
                        Carrinho.getListaDeProdutosNoCarrinho().removeIf(item -> item.getId() == id);
                    } else {
                        utils.imprimirMensagem("\nO produto informado não está no carrinho.");
                    }
                    
                    utils.pausar();
                    break;

                case 4:
                    alterarQuantidadeNoCarrinho();
                    utils.pausar();
                    break;

                case 0:
                    looping = false;
                    break;

                default:
                    utils.imprimirMensagem("O valor inserido não condiz com nenhuma das opções.\nInsira um valor entre 0 - 4.\n");
                    utils.pausar();
                    break;
            }
        }
    }

    /** Altera a quantidade de um item do carrinho (o id é o mostrado no carrinho). */
    private void alterarQuantidadeNoCarrinho () {
        int id = leitorDeCarrinho.lerId();
        Carrinho item = null;

        for (Carrinho produtoNoCarrinho : Carrinho.getListaDeProdutosNoCarrinho()) {
            if (produtoNoCarrinho.getId() == id) {
                item = produtoNoCarrinho;
                break;
            }
        }

        if (item == null) {
            utils.imprimirMensagem("\nO produto informado não está no carrinho.");
            return;
        }

        int quantidade = leitorDeCarrinho.lerQuantidade();
        Produto produto = Produto.buscarPorId(Produto.getProdutos(), item.getIdProduto());

        if (produto == null) {
            utils.imprimirMensagem("\nEste produto não existe mais. Exclua-o do carrinho.");
        } else if (produto.getQuantidade() == 0) {
            utils.imprimirMensagem("\nEste produto está esgotado. Retire-o do carrinho.");
        } else if (quantidade > produto.getQuantidade()) {
            utils.imprimirMensagem("\nA quantidade inserida é maior do que a contida no estoque (" + produto.getQuantidade() + ").");
        } else {
            item.setQuantidade(quantidade);
            utils.imprimirMensagem("\nQuantidade alterada.");
        }
    }

    /** Mostra as compras do cliente (id, data e hora, forma de pagamento, valor final) e o total gasto. */
    public void imprimirHistoricoDeCompras (Usuario usuario) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        List<Compra> compras = Compra.getHistorico(usuario.getNome());
        BigDecimal totalGasto = BigDecimal.ZERO;

        utils.limparTerminal();

        if (compras.isEmpty()) {
            utils.imprimirMensagem("Você ainda não fez nenhuma compra.\n");
            utils.pausar();
            return;
        }

        for (Compra compra : compras) {
            utils.imprimirMensagem("══════════════════════════════════════\n\n");
            utils.imprimirMensagem("Compra nº " + compra.getId() + "\n");
            utils.imprimirMensagem("Data e hora: " + compra.getDataHora().format(formato) + "\n");
            utils.imprimirMensagem("Forma de pagamento: " + compra.getFormaDePagamento() + "\n");
            utils.imprimirMensagem("Valor final pago: " + utils.formatarMoeda(compra.getValorPago()) + "\n\n");

            totalGasto = totalGasto.add(compra.getValorPago());
        }

        utils.imprimirMensagem("══════════════════════════════════════\n\n");
        utils.imprimirMensagem("Total gasto em todas as compras: " + utils.formatarMoeda(totalGasto) + "\n");
        utils.pausar();
    }
}
