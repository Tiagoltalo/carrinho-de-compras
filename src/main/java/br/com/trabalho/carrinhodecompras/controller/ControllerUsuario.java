package br.com.trabalho.carrinhodecompras.controller;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import br.com.trabalho.carrinhodecompras.io.UsuarioView;
import br.com.trabalho.carrinhodecompras.io.LeitorDeOpcao;
import br.com.trabalho.carrinhodecompras.io.LeitorDeCarrinho;
import br.com.trabalho.carrinhodecompras.model.Carrinho;
import br.com.trabalho.carrinhodecompras.model.Compra;
import br.com.trabalho.carrinhodecompras.model.Produto;
import br.com.trabalho.carrinhodecompras.model.Usuario;
import br.com.trabalho.carrinhodecompras.exception.LeituraInvalidaException;
import br.com.trabalho.carrinhodecompras.exception.ProdutoDuplicadoException;
import br.com.trabalho.carrinhodecompras.utils.Utils;

public class ControllerUsuario {
    private final UsuarioView usuarioView = new UsuarioView();
    private final ControllerCompra controllerCompra = new ControllerCompra();
    private final LeitorDeOpcao leitorDeOpcao = new LeitorDeOpcao();
    private final LeitorDeCarrinho leitorDeCarrinho = new LeitorDeCarrinho();
    private final Utils utils = new Utils();

    public void listarProdutos () {
        int opcao;
        boolean looping = true;

        while (looping) {
            utils.limparTerminal();

            for (Produto produto : Produto.getProdutos()) {
                int id = produto.getId();
                String nome = produto.getNome();
                BigDecimal preco = produto.getPreco();
                int quantidade = produto.getQuantidade();
    
                usuarioView.imprimirProduto(id, nome, preco, quantidade);
            }
    
            utils.imprimirMensagem("══════════════════════════════════════\n\n");
            utils.imprimirMensagem("1: adicionar produto ao carrinho | 0: retornar ao menu\n\n");

            opcao = leitorDeOpcao.lerOpcao();

            switch (opcao) {
                case 1:
                    int id;
                    Produto produto = null;

                    try {
                        int idProduto = leitorDeCarrinho.lerId();
                        int quantidade = leitorDeCarrinho.lerQuantidade();
                        produto = Produto.buscarPorId(Produto.getProdutos(), idProduto);
                        String nome = produto.getNome();
                        BigDecimal preco = produto.getPreco();
                        
                        if (quantidade > produto.getQuantidade()) {
                            throw new LeituraInvalidaException(String.valueOf(quantidade), "quantidade", "o valor inserido é maior do que o contido no estoque.");
                        }

                        try {
                            id = Carrinho.buscarUltimoId(Carrinho.getListaDeProdutosNoCarrinho()) + 1;
    
                            Carrinho produtoNoCarrinho = new Carrinho(id, idProduto, nome, preco, quantidade);
                            Carrinho.adicionarProdutoAOCarrinho(produtoNoCarrinho);
                            utils.imprimirMensagem("\nProduto adicionado ao carrinho");
                            
                        } catch (ProdutoDuplicadoException e) {
                            utils.imprimirMensagem("\nO produto já foi adicionado ao carrinho.");
                        } catch (LeituraInvalidaException e) {
                            utils.imprimirMensagem(e.getMessage() + "\n\n");
                        }

                    } catch (LeituraInvalidaException e) {
                        utils.imprimirMensagem(e.getMessage() + "\n");
                    } catch (NullPointerException e) {
                        utils.imprimirMensagem("\nO produto informado não existe.");
                    }



                    utils.pausar();
                    break;
                case 0:
                    looping = false;
                    break;
                default:
                    utils.imprimirMensagem("O valor inserido não condiz com nenhuma das opções.\nInsira um valor entre 0 - 1.\n");
                    utils.pausar();
                    break;
            }
        }
    }

    public void acessarCarrinho (Usuario usuario) {
        int opcao;
        boolean looping = true;

        while (looping) {
            utils.limparTerminal();

            for (Carrinho produtoNoCarrinho : Carrinho.getListaDeProdutosNoCarrinho()) {
                int id = produtoNoCarrinho.getId();
                String nome = produtoNoCarrinho.getNome();
                BigDecimal preco = produtoNoCarrinho.getPreco();
                int quantidade = produtoNoCarrinho.getQuantidade();

    
                usuarioView.imprimirProduto(id, nome, preco, quantidade);
            }
    
            utils.imprimirMensagem("══════════════════════════════════════\n\n");
            utils.imprimirMensagem("Subtotal: " + utils.formatarMoeda(Carrinho.calcularSubtotal()) + "\n\n");
            utils.imprimirMensagem("1: Área de Pagamento | 2: Excluir Produto do Carrinho | 3: Alterar Quantidade | 0: retornar ao menu\n\n");

            opcao = leitorDeOpcao.lerOpcao();

            switch (opcao) {
                case 1:
                    controllerCompra.finalizarCompra(usuario);
                    utils.pausar();
                    break;

                case 2:
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

                case 3:
                    alterarQuantidadeNoCarrinho();
                    utils.pausar();
                    break;

                case 0:
                    looping = false;
                    break;

                default:
                    utils.imprimirMensagem("O valor inserido não condiz com nenhuma das opções.\nInsira um valor entre 0 - 3.\n");
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
            utils.imprimirMensagem("Compra: " + compra.getId() + "\n");
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
