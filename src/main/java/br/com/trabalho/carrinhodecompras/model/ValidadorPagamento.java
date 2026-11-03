package br.com.trabalho.carrinhodecompras.model;

import java.util.regex.Pattern;
import br.com.trabalho.carrinhodecompras.exception.LeituraInvalidaException;

/**
 * Validações simples dos dados de pagamento (é uma simulação, não um pagamento real).
 * Mesmo papel de {@link ValidadorProduto}: ficam num só lugar para serem
 * usadas na digitação e nos construtores das formas de pagamento.
 */
public final class ValidadorPagamento {
    private static final Pattern NUMERO_DO_CARTAO = Pattern.compile("\\d{16}");
    private static final Pattern CVV = Pattern.compile("\\d{3}");

    private ValidadorPagamento () {}

    /** Aceita espaços e hífens ("1111 2222 3333 4444") e devolve só os 16 dígitos. */
    public static String validarNumeroDoCartao (String texto) {
        String valor = texto == null ? "" : texto.replaceAll("[ -]", "");

        if (!NUMERO_DO_CARTAO.matcher(valor).matches()) {
            throw new LeituraInvalidaException(texto, "número do cartão", "o número deve ter 16 dígitos.");
        }
        return valor;
    }

    public static String validarCvv (String texto) {
        String valor = texto == null ? "" : texto.trim();

        if (!CVV.matcher(valor).matches()) {
            throw new LeituraInvalidaException(texto, "CVV", "o CVV deve ter 3 dígitos.");
        }
        return valor;
    }
}
