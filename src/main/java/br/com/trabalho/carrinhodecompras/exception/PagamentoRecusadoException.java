package br.com.trabalho.carrinhodecompras.exception;

public class PagamentoRecusadoException extends RuntimeException {
    public PagamentoRecusadoException (String motivo) {
        super("Pagamento recusado: " + motivo);
    }
}
