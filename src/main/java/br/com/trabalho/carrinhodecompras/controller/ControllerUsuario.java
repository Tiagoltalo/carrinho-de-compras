package br.com.trabalho.carrinhodecompras.controller;

import br.com.trabalho.carrinhodecompras.io.UsuarioView;
import br.com.trabalho.carrinhodecompras.io.LeitorDeOpcao;
import br.com.trabalho.carrinhodecompras.io.LeitorDeCarrinho;
import br.com.trabalho.carrinhodecompras.model.Carrinho;
import br.com.trabalho.carrinhodecompras.model.Produto;
import br.com.trabalho.carrinhodecompras.exception.LeituraInvalidaException;
import br.com.trabalho.carrinhodecompras.exception.ProdutoDuplicadoException;
import br.com.trabalho.carrinhodecompras.utils.Utils;

public class ControllerUsuario {
    private final UsuarioView usuarioView = new UsuarioView();
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
                String preco = produto.getPrecoFormatado();
                int quantidade = produto.getQuantidade();
    
                usuarioView.imprimirProduto(id, nome, preco, quantidade);
            }
    
            utils.imprimirMensagem("══════════════════════════════════════\n\n");
            utils.imprimirMensagem("1: adicionar produto ao carrinho | 0: retornar ao menu\n\n");

            opcao = leitorDeOpcao.lerOpcao();

            switch (opcao) {
                case 1:
                    int id = leitorDeCarrinho.lerId();
                    int quantidade = leitorDeCarrinho.lerQuantidade();
                    Produto produto = Produto.buscarPorId(Produto.getProdutos(), id);

                    if (quantidade > produto.getQuantidade()) {
                        throw new LeituraInvalidaException(String.valueOf(quantidade), "quantidade", "o valor inserido é maior do que o contido no estoque.");
                    }

                    if (produto != null) {
                        try {
                            id = Carrinho.buscarUltimoId(Carrinho.getListaDeProdutosNoCarrinho());
    
                            Carrinho produtoNoCarrinho = new Carrinho(id, produto, quantidade);
                            Carrinho.adicionarProdutoAOCarrinho(produtoNoCarrinho);
                            utils.imprimirMensagem("\nProduto adicionado ao carrinho");
                            
                        } catch (ProdutoDuplicadoException e) {
                            utils.imprimirMensagem("O produto já foi adicionado ao carrinho.\n\n");
                        } catch (LeituraInvalidaException e) {
                            utils.imprimirMensagem(e.getMessage() + "\n\n");
                        }
                    } else {
                        utils.imprimirMensagem("\nO produto informado não existe");
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

    public void acessarCarrinho () {
        int opcao;
        double subtotal = 0;
        boolean looping = true;

        while (looping) {
            utils.limparTerminal();

            for (Carrinho produtoNoCarrinho : Carrinho.getListaDeProdutosNoCarrinho()) {
                int id = produtoNoCarrinho.getId();
                String nome = produtoNoCarrinho.getProduto().getNome();
                String preco = produtoNoCarrinho.getProduto().getPrecoFormatado();
                int quantidade = produtoNoCarrinho.getQuantidade();

                subtotal += produtoNoCarrinho.getProduto().getPreco().doubleValue() * quantidade;
    
                usuarioView.imprimirProduto(id, nome, preco, quantidade);
            }
    
            utils.imprimirMensagem("══════════════════════════════════════\n\n");
            utils.imprimirMensagem("Subtotal: R$ " + subtotal + "\n\n");
            utils.imprimirMensagem("1: Cartão de Débito | 2: Cartão de Crédito | 3: Pix | 0: retornar ao menu\n\n");

            opcao = leitorDeOpcao.lerOpcao();

            switch (opcao) {
                case 1:
                    utils.imprimirMensagem("Pagou com Cartão de Débito...");
                    utils.pausar();
                    break;

                case 2:
                    utils.imprimirMensagem("Pagou com Cartão de Crédito...");
                    utils.pausar();
                    break;

                case 3:
                    utils.imprimirMensagem("Pagou com Pix...");
                    utils.pausar();
                    break;

                case 0:
                    looping = false;
                    break;

                default:
                    break;
            }
        }
    }

    public void imprimirHistoricoDeCompras () {

    }
}
