package br.com.trabalho.carrinhodecompras.controller;

import br.com.trabalho.carrinhodecompras.io.UsuarioView;
import br.com.trabalho.carrinhodecompras.io.LeitorDeOpcao;
import br.com.trabalho.carrinhodecompras.io.LeitorDeCarrinho;
import br.com.trabalho.carrinhodecompras.model.Carrinho;
import br.com.trabalho.carrinhodecompras.model.Produto;

import java.math.BigDecimal;

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

    public void acessarCarrinho () {
        int opcao;
        double subtotal = 0;
        boolean looping = true;

        while (looping) {
            utils.limparTerminal();

            for (Carrinho produtoNoCarrinho : Carrinho.getListaDeProdutosNoCarrinho()) {
                int id = produtoNoCarrinho.getId();
                String nome = produtoNoCarrinho.getNome();
                BigDecimal preco = produtoNoCarrinho.getPreco();
                int quantidade = produtoNoCarrinho.getQuantidade();

                subtotal += produtoNoCarrinho.getPreco().doubleValue() * quantidade;
    
                usuarioView.imprimirProduto(id, nome, preco, quantidade);
            }
    
            utils.imprimirMensagem("══════════════════════════════════════\n\n");
            utils.imprimirMensagem("Subtotal: R$ " + subtotal + "\n\n");
            utils.imprimirMensagem("1: Cartão de Débito | 2: Cartão de Crédito | 3: Pix | 4: Excluir Produto do Carrinho | 0: retornar ao menu\n\n");

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

                case 4:
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
                        Carrinho.getListaDeProdutosNoCarrinho().remove(Carrinho.buscarPorId(Carrinho.getListaDeProdutosNoCarrinho(), id));
                    } else {
                        utils.imprimirMensagem("\nO produto informado não está no carrinho.");
                    }
                    
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
