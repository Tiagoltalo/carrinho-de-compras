package br.com.trabalho.carrinhodecompras.model;

import java.math.BigDecimal;

public record ItemComprado(int idProduto, String nome, BigDecimal precoUnitario, int quantidade) {

    public BigDecimal subtotal () {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}
