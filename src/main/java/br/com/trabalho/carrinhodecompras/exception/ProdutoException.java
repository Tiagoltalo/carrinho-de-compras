package br.com.trabalho.carrinhodecompras.exception;

public class ProdutoException extends RuntimeException {
    public ProdutoException (String mensagem) {
        super(mensagem);
    }
}