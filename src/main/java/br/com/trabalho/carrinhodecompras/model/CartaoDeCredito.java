package br.com.trabalho.carrinhodecompras.model;

import java.math.BigDecimal;

/** Cartão de crédito: paga o valor integral da compra (sem desconto). */
public class CartaoDeCredito extends CartaoDePagamento {
    public CartaoDeCredito (String numero, String cvv) {
        super("Cartão de Crédito", numero, cvv);
    }

    @Override
    public BigDecimal calcularValorFinal (BigDecimal valorDaCompra) {
        return valorDaCompra;
    }

    @Override
    protected String processarPagamento (BigDecimal valorFinal) {
        // Simulação: a operadora aprova a compra no crédito.
        return gerarCodigoDeAutorizacao("CRD");
    }
}
