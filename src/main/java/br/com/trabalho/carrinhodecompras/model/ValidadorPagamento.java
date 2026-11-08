package br.com.trabalho.carrinhodecompras.model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import br.com.trabalho.carrinhodecompras.exception.LeituraInvalidaException;

public final class ValidadorPagamento {
    private static final Pattern NUMERO_DO_CARTAO = Pattern.compile("\\d{16}");
    private static final Pattern CVV = Pattern.compile("\\d{3}");
    private static final Pattern VALIDADE = Pattern.compile("(\\d{2})\\s*/\\s*(\\d{2}|\\d{4})");

    private ValidadorPagamento () {}

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

    public static YearMonth validarValidade (String texto) {
        String valor = texto == null ? "" : texto.trim();
        Matcher m = VALIDADE.matcher(valor);

        if (!m.matches()) {
            throw new LeituraInvalidaException(texto, "validade", "use o formato MM/AA (ex.: 08/28).");
        }

        int mes = Integer.parseInt(m.group(1));
        int ano = Integer.parseInt(m.group(2));
        if (m.group(2).length() == 2) ano += 2000;

        if (mes < 1 || mes > 12) {
            throw new LeituraInvalidaException(texto, "validade", "o mês deve estar entre 01 e 12.");
        }

        YearMonth validade = YearMonth.of(ano, mes);
        if (validade.isBefore(YearMonth.from(LocalDate.now()))) {
            throw new LeituraInvalidaException(texto, "validade", "cartão vencido.");
        }
        return validade;
    }
}
