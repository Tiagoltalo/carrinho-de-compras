package br.com.trabalho.carrinhodecompras.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public abstract class Produto {
    private final int id; 
    private String nome;
    private BigDecimal preco;
    private int quantidade;

    protected Produto (int id, String nome, BigDecimal preco, int quantidade) {
        this.id = ValidadorProduto.validarId(id);
        this.nome = ValidadorProduto.validarNome(nome);
        this.preco = ValidadorProduto.validarPreco(preco);
        this.quantidade = ValidadorProduto.validarQuantidade(quantidade);
    }

    public abstract String getCategoria ();

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

    public boolean temEstoque () {
        return quantidade > 0;
    }

    /** Preço formatado em reais, ex: "R$ 19,90". */
    public String getPrecoFormatado () {
        return NumberFormat.getCurrencyInstance(Locale.of("pt", "BR")).format(preco);
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
