package br.com.trabalho.carrinhodecompras.io;

import br.com.trabalho.carrinhodecompras.exception.LeituraInvalidaException;
import br.com.trabalho.carrinhodecompras.model.CartaoDeCredito;
import br.com.trabalho.carrinhodecompras.model.CartaoDeDebito;
import br.com.trabalho.carrinhodecompras.model.ValidadorPagamento;
import br.com.trabalho.carrinhodecompras.utils.Utils;

public class LeitorDePagamento {
    private final Utils utils = new Utils();

    // ---------- formas de pagamento ----------
    public CartaoDeCredito lerCartaoDeCredito () {
        utils.imprimirMensagem("=== Cartão de Crédito ===\n\n");

        return new CartaoDeCredito(lerNumeroDoCartao(), lerCvv(), lerValidade());
    }

    public CartaoDeDebito lerCartaoDeDebito () {
        utils.imprimirMensagem("=== Cartão de Débito ===\n\n");

        return new CartaoDeDebito(lerNumeroDoCartao(), lerCvv(), lerValidade());
    }

    // ---------- campos ----------
    private String lerNumeroDoCartao () {
        while (true) {
            System.out.print("Digite o número do cartão (16 dígitos): ");
            String texto = utils.lerTexto();
            try {
                ValidadorPagamento.validarNumeroDoCartao(texto);
                return texto;
            } catch (LeituraInvalidaException e) {
                utils.imprimirMensagem(e.getMessage() + "\n\n");
            }
        }
    }

    private String lerCvv () {
        while (true) {
            System.out.print("Digite o CVV (3 dígitos): ");
            String texto = utils.lerTexto();
            try {
                ValidadorPagamento.validarCvv(texto);
                return texto;
            } catch (LeituraInvalidaException e) {
                utils.imprimirMensagem(e.getMessage() + "\n\n");
            }
        }
    }

    private String lerValidade () {
        while (true) {
            System.out.print("Digite a validade (MM/AA): ");
            String texto = utils.lerTexto();
            try {
                ValidadorPagamento.validarValidade(texto);
                return texto;
            } catch (LeituraInvalidaException e) {
                utils.imprimirMensagem(e.getMessage() + "\n\n");
            }
        }
    }
}
