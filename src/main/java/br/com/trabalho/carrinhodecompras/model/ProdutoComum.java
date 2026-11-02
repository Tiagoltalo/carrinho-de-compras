package br.com.trabalho.carrinhodecompras.model;

import java.math.BigDecimal;

public class ProdutoComum extends Produto {
    public ProdutoComum (int id, String nome, BigDecimal preco, int quantidade) {
        super(id, nome, preco, quantidade);
    }

    @Override
    public String getCategoria () {
        return "Comum";
    }
}
