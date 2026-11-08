package br.com.trabalho.carrinhodecompras.model;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class Pix extends FormaDePagamento {
    private static final int DESCONTO_EM_PERCENTUAL = 5;
    private final String chave = UUID.randomUUID().toString(); 

    public Pix () {
        super("Pix");
    }

    @Override
    public BigDecimal calcularValorFinal (BigDecimal valorDaCompra) {
        return aplicarDesconto(valorDaCompra, DESCONTO_EM_PERCENTUAL);
    }

    @Override
    protected void processarPagamento (BigDecimal valorFinal) {
    }

    @Override
    public Optional<String> getInstrucoesDePagamento () {
        return Optional.of("Chave Pix (copie e cole no app do seu banco):\n" + chave);
    }

    public String getChave () { return chave; }
}
