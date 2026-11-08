package br.com.trabalho.carrinhodecompras.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import br.com.trabalho.carrinhodecompras.exception.PagamentoRecusadoException;

public abstract class FormaDePagamento {
    private final String nome;

    protected FormaDePagamento (String nome) {
        this.nome = nome;
    }

    public abstract BigDecimal calcularValorFinal (BigDecimal valorDaCompra);

    protected abstract String processarPagamento (BigDecimal valorFinal);

    public final ComprovanteDePagamento pagar (BigDecimal valorDaCompra) {
        if (valorDaCompra == null || valorDaCompra.signum() <= 0) {
            throw new PagamentoRecusadoException("o valor da compra deve ser maior que zero.");
        }

        BigDecimal valorFinal = calcularValorFinal(valorDaCompra);
        String codigoDeAutorizacao = processarPagamento(valorFinal);

        return new ComprovanteDePagamento(nome, valorDaCompra, valorFinal, codigoDeAutorizacao, LocalDateTime.now());
    }

    public Optional<String> getInstrucoesDePagamento () {
        return Optional.empty();
    }

    protected static BigDecimal aplicarDesconto (BigDecimal valor, int percentual) {
        return valor.multiply(BigDecimal.valueOf(100 - percentual))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    protected static String gerarCodigoDeAutorizacao (String prefixo) {
        return prefixo + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public String getNome () { return nome; }
}
