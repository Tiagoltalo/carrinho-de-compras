package br.com.trabalho.carrinhodecompras.model;

import java.math.BigDecimal;

/** Cartão de débito: 10% de desconto no valor da compra. */
public class CartaoDeDebito extends CartaoDePagamento {
    private static final int DESCONTO_EM_PERCENTUAL = 10;

    public CartaoDeDebito (String numero, String cvv) {
        super("Cartão de Débito", numero, cvv);
    }

    @Override
    public BigDecimal calcularValorFinal (BigDecimal valorDaCompra) {
        return aplicarDesconto(valorDaCompra, DESCONTO_EM_PERCENTUAL);
    }

    @Override
    protected String processarPagamento (BigDecimal valorFinal) {
        // Simulação: o valor é debitado da conta do cliente.
        return gerarCodigoDeAutorizacao("DEB");
    }
}
