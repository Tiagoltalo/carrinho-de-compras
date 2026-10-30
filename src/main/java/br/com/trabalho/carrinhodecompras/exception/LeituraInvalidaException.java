package br.com.trabalho.carrinhodecompras.exception;

public class LeituraInvalidaException extends RuntimeException {
    private final String valor;
    private final String campo;

    public LeituraInvalidaException (String valor, String campo, String motivo) {
        super(String.format("Valor inválido - %s: %s", campo, motivo));
        this.valor = valor;
        this.campo = campo;
    }

    public LeituraInvalidaException (String valor, String campo, String motivo, Throwable causa) {
        super(String.format("Valor inválido - %s: %s", campo, motivo), causa);
        this.valor = valor;
        this.campo = campo;
    }
    
    public String getValor () { return valor; }

    public String getCampo () { return campo; }
}
