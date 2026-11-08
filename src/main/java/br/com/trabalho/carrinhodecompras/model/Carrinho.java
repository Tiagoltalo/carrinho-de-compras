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

    /** Esvazia o carrinho (usado ao finalizar a compra e quando o cliente sai). */
    public static void limpar () {
        listaDeProdutosNoCarrinho.clear();
    }

    /** Soma preço x quantidade de todos os itens, sem erro de arredondamento. */
    public static BigDecimal calcularSubtotal () {
        BigDecimal subtotal = BigDecimal.ZERO;

        for (Carrinho produtoNoCarrinho : listaDeProdutosNoCarrinho) {
            subtotal = subtotal.add(produtoNoCarrinho.getPreco().multiply(BigDecimal.valueOf(produtoNoCarrinho.getQuantidade())));
        }

        return subtotal;
    }

    public int getId () { return this.id; }

    public int getIdProduto () { return this.idProduto; }

    public String getNome () { return this.nome; }

    public BigDecimal getPreco () { return this.preco; }

    public int getQuantidade () {return this.quantidade; }

    public void setQuantidade (int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        this.quantidade = quantidade;
    }

    public static List<Carrinho> getListaDeProdutosNoCarrinho () { return listaDeProdutosNoCarrinho; }

    public static List<Carrinho> getListaDeProdutosComprados () { return listaDeProdutosComprados; }
}
