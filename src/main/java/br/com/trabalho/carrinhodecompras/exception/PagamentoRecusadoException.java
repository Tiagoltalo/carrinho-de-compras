package br.com.trabalho.carrinhodecompras.exception;

/**
 * Lançada quando uma forma de pagamento não consegue aprovar a transação
 * (ex: cartão vencido no momento do pagamento).
 */
public class PagamentoRecusadoException extends RuntimeException {
    public PagamentoRecusadoException (String motivo) {
        super("Pagamento recusado: " + motivo);
    }
}
