package br.com.trabalho.carrinhodecompras.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import br.com.trabalho.carrinhodecompras.exception.ProdutoDuplicadoException;

public abstract class Produto {
    private static List<Produto> produtos = new ArrayList<>();
    private final int id; // imutável: é a identidade do produto
    private String nome;
    private BigDecimal preco;
    private int quantidade;

    protected Produto (int id, String nome, BigDecimal preco, int quantidade) {
        this.id = ValidadorProduto.validarId(id);
        this.nome = ValidadorProduto.validarNome(nome);
        this.preco = ValidadorProduto.validarPreco(preco);
        this.quantidade = ValidadorProduto.validarQuantidade(quantidade);
    }

    /** Cada subclasse diz a que categoria pertence (polimorfismo). */
    public abstract String getCategoria ();

    // ---------- lista de produtos (mesmo padrão de Usuario) ----------
    public static void adicionarProdutoALista (Produto produto) {
        if (buscarPorId(produtos, produto.getId()) != null) {
            throw new ProdutoDuplicadoException(produto.getId());
        }

        produtos.add(produto);
    }

    public static void removerProdutoDaLista (Produto produto) {
        produtos.remove(produto);
    }

    /** Devolve o maior id existente (0 se a lista estiver vazia). O próximo id é este valor + 1. */
    public static int buscarUltimoId (List<Produto> produtos) {
        int ultimoId = 0;

        for (Produto produto : produtos) {
            if (ultimoId < produto.getId()) {
                ultimoId = produto.getId();
            }
        }

        return ultimoId;
    }

    /** Devolve o produto com o id informado, ou {@code null} se não existir. */
    public static Produto buscarPorId (List<Produto> produtos, int id) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                return produto;
            }
        }

        return null;
    }

    public static List<Produto> getProdutos () { return produtos; }

    // ---------- dados do produto ----------
    public int getId () { return id; }

    public String getNome () { return nome; }

    public BigDecimal getPreco () { return preco; }

    public int getQuantidade () { return quantidade; }

    public void setNome (String nome) {
        this.nome = ValidadorProduto.validarNome(nome);
    }

    public void setPreco (BigDecimal preco) {
        this.preco = ValidadorProduto.validarPreco(preco);
    }

    public void setQuantidade (int quantidade) {
        this.quantidade = ValidadorProduto.validarQuantidade(quantidade);
    }

    /** Preço formatado em reais, ex: "R$ 19,90". */
    public String getPrecoFormatado () {
        return NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR")).format(preco);
    }

    @Override
    public String toString () {
        return String.format("[%d] %s (%s) - %s - Estoque: %d",
                id, nome, getCategoria(), getPrecoFormatado(), quantidade);
    }

    /** Dois produtos são o mesmo produto se tiverem o mesmo id. */
    @Override
    public boolean equals (Object outro) {
        if (this == outro) return true;
        if (!(outro instanceof Produto)) return false;
        return id == ((Produto) outro).id;
    }

    @Override
    public int hashCode () {
        return Integer.hashCode(id);
    }
}
