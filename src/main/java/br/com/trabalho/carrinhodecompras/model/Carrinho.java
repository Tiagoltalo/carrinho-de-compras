package br.com.trabalho.carrinhodecompras.model;

import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import br.com.trabalho.carrinhodecompras.exception.ProdutoDuplicadoException;

public class Carrinho {
    private static List<Carrinho> listaDeProdutosNoCarrinho = new ArrayList<>();
    private static List<Carrinho> listaDeProdutosComprados = new ArrayList<>();
    private int id;
    private int idProduto;
    private String nome;
    private BigDecimal preco;
    private int quantidade;

    public Carrinho (int id, int idProduto, String nome, BigDecimal preco, int quantidade) {
        this.id = id;
        this.idProduto = idProduto;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public static void adicionarProdutoAOCarrinho (Carrinho produtoNoCarrinho) {
        if (buscarPorId(listaDeProdutosNoCarrinho, produtoNoCarrinho.getIdProduto()) != null) {
            throw new ProdutoDuplicadoException(produtoNoCarrinho.getIdProduto());
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

    public static Carrinho buscarPorId (List<Carrinho> produtosNoCarrinho, int id) {
        for (Carrinho produtoNoCarrinho : produtosNoCarrinho) {
            if (produtoNoCarrinho.getIdProduto() == id) {
                return produtoNoCarrinho;
            }
        }

        return null;
    }

    public int getId () { return this.id; }

    public int getIdProduto () { return this.idProduto; }

    public String getNome () { return this.nome; }

    public BigDecimal getPreco () { return this.preco; }

    public int getQuantidade () {return this.quantidade; }

    public static List<Carrinho> getListaDeProdutosNoCarrinho () { return listaDeProdutosNoCarrinho; }

    public static List<Carrinho> getListaDeProdutosComprados () { return listaDeProdutosComprados; }
}
