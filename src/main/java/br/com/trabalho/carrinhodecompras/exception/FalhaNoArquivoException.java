package br.com.trabalho.carrinhodecompras.exception;

public class FalhaNoArquivoException extends RuntimeException {
    public FalhaNoArquivoException (String local, String motivo) {
        super(String.format("Erro no arquivo - %s: %s", local, motivo));
    }

    public FalhaNoArquivoException (String local, String motivo, Throwable causa) {
        super(String.format("Erro no arquivo - %s: %s", local, motivo), causa);
    }
}
