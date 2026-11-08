package br.com.trabalho.carrinhodecompras.model;

import java.math.BigDecimal;

public class CartaoDeCredito extends CartaoDePagamento {
    public CartaoDeCredito (String numero, String cvv, String validade) {
        super("Cartão de Crédito", numero, cvv, validade);
    }

    @Override
    public BigDecimal calcularValorFinal (BigDecimal valorDaCompra) {
        return valorDaCompra;
    }

    @Override
    protected void processarPagamento (BigDecimal valorFinal) {
        // Simulação: a operadora aprova a compra no crédito.
    }
}
