package br.com.trabalho.carrinhodecompras.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Resultado de um pagamento aprovado.
 *
 * É um {@code record}: imutável, e o Java gera sozinho o construtor, os métodos
 * de acesso (ex: {@code comprovante.valorPago()}), equals, hashCode e toString.
 */
public record ComprovanteDePagamento(
        String formaDePagamento,
        BigDecimal valorDaCompra,
        BigDecimal valorPago,
        String codigoDeAutorizacao,
        LocalDateTime dataHora) {

    /** Quanto o cliente economizou em relação ao valor da compra (zero se não houve desconto). */
    public BigDecimal desconto () {
        return valorDaCompra.subtract(valorPago);
    }
}
