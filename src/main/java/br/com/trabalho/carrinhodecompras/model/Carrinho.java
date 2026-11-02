package br.com.trabalho.carrinhodecompras.model;

import java.util.List;
import java.util.ArrayList;
import br.com.trabalho.carrinhodecompras.exception.ProdutoDuplicadoException;

public class Carrinho {
    private static List<Carrinho> listaDeProdutosNoCarrinho = new ArrayList<>();
    private int id;
    private Produto produto;
    private int quantidade;

    public Carrinho (int id, Produto produto, int quantidade) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public static void adicionarProdutoAOCarrinho (Carrinho produtoNoCarrinho) {
        Produto produto = produtoNoCarrinho.getProduto();

        if (buscarPorId(listaDeProdutosNoCarrinho, produto.getId()) != null) {
            throw new ProdutoDuplicadoException(produto.getId());
        }

        listaDeProdutosNoCarrinho.add(produtoNoCarrinho);
    }

    public static int buscarUltimoId (List<Carrinho> produtosNoCarrinho) {
        int ultimoId = 0;

        for (Carrinho produtoNoCarrinho : produtosNoCarrinho) {
            if (ultimoId < produtoNoCarrinho.getId()) {
                ultimoId = produtoNoCarrinho.getId();
            }
        }

        return ultimoId;
    }

    public static Produto buscarPorId (List<Carrinho> produtosNoCarrinho, int id) {
        for (Carrinho produtoNoCarrinho : produtosNoCarrinho) {
            Produto produto = produtoNoCarrinho.getProduto();

            if (produto.getId() == id) {
                return produto;
            }
        }

        return null;
    }

    public int getId () { return this.id; }

    public Produto getProduto () { return this.produto; }

    public int getQuantidade () {return this.quantidade; }

    public static List<Carrinho> getListaDeProdutosNoCarrinho () { return listaDeProdutosNoCarrinho; }
}
