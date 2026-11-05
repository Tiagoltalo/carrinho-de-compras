package br.com.trabalho.carrinhodecompras.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import br.com.trabalho.carrinhodecompras.exception.PagamentoRecusadoException;

/**
 * Classe abstrata que representa uma forma de pagamento.
 *
 * A compra só conhece este tipo: ela chama {@link #pagar(BigDecimal)} e cada
 * subclasse responde do seu jeito (polimorfismo), sem nenhum if/switch para
 * descobrir se o pagamento é crédito, débito ou Pix.
 *
 * {@code pagar} é um "template method": o passo a passo é sempre o mesmo
 * (validar valor → calcular valor final → processar → gerar comprovante),
 * mas {@link #calcularValorFinal} e {@link #processarPagamento} são
 * obrigação de cada forma de pagamento.
 */
public abstract class FormaDePagamento {
    private final String nome;

    protected FormaDePagamento (String nome) {
        this.nome = nome;
    }

    /** Valor que o cliente realmente paga (com o desconto, se a forma de pagamento tiver). */
    public abstract BigDecimal calcularValorFinal (BigDecimal valorDaCompra);

    /**
     * Simula o processamento do pagamento.
     *
     * @return o código de autorização da transação
     * @throws PagamentoRecusadoException se alguma condição necessária não for atendida
     */
    protected abstract String processarPagamento (BigDecimal valorFinal);

    public final ComprovanteDePagamento pagar (BigDecimal valorDaCompra) {
        if (valorDaCompra == null || valorDaCompra.signum() <= 0) {
            throw new PagamentoRecusadoException("o valor da compra deve ser maior que zero.");
        }

        BigDecimal valorFinal = calcularValorFinal(valorDaCompra);
        String codigoDeAutorizacao = processarPagamento(valorFinal);

        return new ComprovanteDePagamento(nome, valorDaCompra, valorFinal, codigoDeAutorizacao, LocalDateTime.now());
    }

    /**
     * Texto que o usuário precisa ver antes de pagar (ex: a chave do Pix).
     * Por padrão não há nada a mostrar; as formas que precisam sobrescrevem.
     */
    public Optional<String> getInstrucoesDePagamento () {
        return Optional.empty();
    }

    /** Desconta o percentual (ex: 10 = 10%) e arredonda para centavos. */
    protected static BigDecimal aplicarDesconto (BigDecimal valor, int percentual) {
        return valor.multiply(BigDecimal.valueOf(100 - percentual))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    /** Código de autorização simulado, ex: "PIX-3F9A1C7B". */
    protected static String gerarCodigoDeAutorizacao (String prefixo) {
        return prefixo + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public String getNome () { return nome; }
}
