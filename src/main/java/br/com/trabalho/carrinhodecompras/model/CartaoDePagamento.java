package br.com.trabalho.carrinhodecompras.model;

/**
 * Parte comum dos pagamentos com cartão (crédito e débito): número e CVV,
 * validados no construtor.
 *
 * Por segurança, o número completo e o CVV não ficam guardados no objeto:
 * só os 4 últimos dígitos, usados para identificar o cartão no comprovante.
 */
public abstract class CartaoDePagamento extends FormaDePagamento {
    private final String finalDoCartao;

    protected CartaoDePagamento (String nome, String numero, String cvv) {
        super(nome);
        String numeroValido = ValidadorPagamento.validarNumeroDoCartao(numero);
        ValidadorPagamento.validarCvv(cvv); // só validado, não é guardado

        this.finalDoCartao = numeroValido.substring(12);
    }

    public String getCartaoMascarado () { return "**** **** **** " + finalDoCartao; }
}
