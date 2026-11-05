package br.com.trabalho.carrinhodecompras.model;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

/**
 * Pix: 5% de desconto no valor da compra.
 *
 * Quem gera a chave é o sistema: o usuário recebe a chave, copia e paga no
 * aplicativo do banco (aqui o pagamento é só simulado). Por isso o Pix não
 * precisa de nenhum dado digitado pelo usuário.
 */
public class Pix extends FormaDePagamento {
    private static final int DESCONTO_EM_PERCENTUAL = 5;
    private final String chave = UUID.randomUUID().toString(); // chave aleatória gerada pelo sistema

    public Pix () {
        super("Pix");
    }

    @Override
    public BigDecimal calcularValorFinal (BigDecimal valorDaCompra) {
        return aplicarDesconto(valorDaCompra, DESCONTO_EM_PERCENTUAL);
    }

    @Override
    protected String processarPagamento (BigDecimal valorFinal) {
        // Simulação: a transferência instantânea para a chave gerada é confirmada.
        return gerarCodigoDeAutorizacao("PIX");
    }

    /** O Pix mostra a chave para o usuário copiar e pagar. */
    @Override
    public Optional<String> getInstrucoesDePagamento () {
        return Optional.of("Chave Pix (copie e cole no app do seu banco):\n" + chave);
    }

    public String getChave () { return chave; }
}
