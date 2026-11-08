package br.com.trabalho.carrinhodecompras.model;

public abstract class CartaoDePagamento extends FormaDePagamento {
    private final String finalDoCartao;

    protected CartaoDePagamento (String nome, String numero, String cvv, String validade) {
        super(nome);
        String numeroValido = ValidadorPagamento.validarNumeroDoCartao(numero);
        ValidadorPagamento.validarCvv(cvv);
        ValidadorPagamento.validarValidade(validade);
        this.finalDoCartao = numeroValido.substring(12);
    }

    public String getCartaoMascarado () { return "**** **** **** " + finalDoCartao; }
}
