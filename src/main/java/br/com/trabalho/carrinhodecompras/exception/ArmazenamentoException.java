package br.com.trabalho.carrinhodecompras.exception;

/**
 * Falha ao ler ou gravar o arquivo de dados (erro de E/S ou arquivo corrompido).
 */
public class ArmazenamentoException extends RuntimeException {
    public ArmazenamentoException (String mensagem) {
        super(mensagem);
    }

    public ArmazenamentoException (String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
