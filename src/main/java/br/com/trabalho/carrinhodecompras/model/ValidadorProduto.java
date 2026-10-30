package br.com.trabalho.carrinhodecompras.model;

import java.math.BigDecimal;
import java.util.regex.Pattern;
import br.com.trabalho.carrinhodecompras.exception.LeituraInvalidaException;

public final class ValidadorProduto {
    public static final int TAMANHO_MAXIMO_NOME = 100;

    private static final Pattern INTEIRO = Pattern.compile("-?\\d+");
    private static final Pattern DECIMAL = Pattern.compile("-?\\d+(\\.\\d+)?");

    private ValidadorProduto () {}

    public static int validarId (String texto) {
        return validarId(lerInteiro(texto, "id"));
    }

    public static int validarId (int id) {
        if (id <= 0) {
            throw new LeituraInvalidaException(String.valueOf(id), "id", "o id deve ser um número inteiro positivo");
        }
        return id;
    }

    public static String validarNome (String nome) {
        String valor = nome == null ? "" : nome.trim();

        if (valor.isEmpty()) {
            throw new LeituraInvalidaException(nome, "nome", "o campo não pode estar vazio");
        }
        if (valor.length() > TAMANHO_MAXIMO_NOME) {
            throw new LeituraInvalidaException(nome, "nome", "o campo não pode ter mais que " + TAMANHO_MAXIMO_NOME + " caracteres");
        }
        
        if (valor.contains(";") || valor.contains("\n") || valor.contains("\r")) {
            throw new LeituraInvalidaException(nome, "nome", "o campo não pode conter ponto e vírgula (;) nem quebra de linha");
        }
        return valor;
    }

    public static BigDecimal validarPreco (String texto) {
        String valor = texto == null ? "" : texto.trim().replace(',', '.');

        if (valor.isEmpty()) {
            throw new LeituraInvalidaException(texto, "preço", "o campo não pode estar vazio");
        }
        if (!DECIMAL.matcher(valor).matches()) {
            throw new LeituraInvalidaException(texto, "preço", "informe apenas números (ex: 19,90)");
        }
        return validarPreco(new BigDecimal(valor));
    }

    public static BigDecimal validarPreco (BigDecimal preco) {
        if (preco == null) {
            throw new LeituraInvalidaException(null, "preço", "o campo não pode estar vazio");
        }
        if (preco.signum() < 0) {
            throw new LeituraInvalidaException(preco.toPlainString(), "preço", "o preço não pode ser negativo");
        }
        if (preco.signum() == 0) {
            throw new LeituraInvalidaException(preco.toPlainString(), "preço", "o preço deve ser maior que zero");
        }
        if (preco.stripTrailingZeros().scale() > 2) {
            throw new LeituraInvalidaException(preco.toPlainString(), "preço", "use no máximo 2 casas decimais");
        }
        return preco.setScale(2);
    }

    public static int validarQuantidade (String texto) {
        return validarQuantidade(lerInteiro(texto, "quantidade"));
    }

    public static int validarQuantidade (int quantidade) {
        if (quantidade < 0) {
            throw new LeituraInvalidaException(String.valueOf(quantidade), "quantidade", "a quantidade em estoque não pode ser negativa");
        }
        return quantidade;
    }

    private static int lerInteiro (String texto, String campo) {
        String valor = texto == null ? "" : texto.trim();

        if (valor.isEmpty()) {
            throw new LeituraInvalidaException(texto, campo, "o campo não pode estar vazio");
        }
        if (!INTEIRO.matcher(valor).matches()) {
            throw new LeituraInvalidaException(texto, campo, "informe um número inteiro (sem letras, vírgulas ou pontos)");
        }
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            throw new LeituraInvalidaException(texto, campo, "o valor está fora do limite permitido", e);
        }
    }
}