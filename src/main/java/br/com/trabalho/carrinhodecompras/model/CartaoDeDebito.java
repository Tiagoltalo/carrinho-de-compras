package br.com.trabalho.carrinhodecompras.model;

import java.math.BigDecimal;

public class CartaoDeDebito extends CartaoDePagamento {
    private static final int DESCONTO_EM_PERCENTUAL = 10;

    public CartaoDeDebito (String numero, String cvv, String validade) {
        super("Cartão de Débito", numero, cvv, validade);
    }

    @Override
    public BigDecimal calcularValorFinal (BigDecimal valorDaCompra) {
        return aplicarDesconto(valorDaCompra, DESCONTO_EM_PERCENTUAL);
    }

    @Override
    protected void processarPagamento (BigDecimal valorFinal) {
        // Simulação: o valor é debitado da conta do cliente.
    }
}
