package br.com.trabalho.carrinhodecompras.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ComprovanteDePagamento(
        String formaDePagamento,
        BigDecimal valorDaCompra,
        BigDecimal valorPago,
        String codigoDeAutorizacao,
        LocalDateTime dataHora) {

    public BigDecimal desconto () {
        return valorDaCompra.subtract(valorPago);
    }
}
