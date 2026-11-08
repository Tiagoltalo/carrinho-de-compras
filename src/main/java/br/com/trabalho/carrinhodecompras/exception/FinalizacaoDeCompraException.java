package br.com.trabalho.carrinhodecompras.exception;

public class FinalizacaoDeCompraException extends RuntimeException {
    public FinalizacaoDeCompraException (String motivo) {
        super("Não foi possível finalizar a compra: " + motivo);
    }
}
