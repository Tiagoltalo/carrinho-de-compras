package br.com.trabalho.carrinhodecompras.io;

import br.com.trabalho.carrinhodecompras.exception.LeituraInvalidaException;
import br.com.trabalho.carrinhodecompras.model.ValidadorProduto;
import br.com.trabalho.carrinhodecompras.utils.Utils;

public class LeitorDeCarrinho {
    private final Utils utils = new Utils();

    public int lerId () {
        while (true) {
            System.out.print("\nDigite o id do produto: ");
            
            try {
                return ValidadorProduto.validarId(utils.lerTexto());
            } catch (LeituraInvalidaException e) {
                utils.imprimirMensagem(e.getMessage() + "\n\n");
            }
        }
    }

    public int lerQuantidade () {
        while (true) {
            System.out.print("Digite a quantidade: ");

            try {
                return ValidadorProduto.validarQuantidade(utils.lerTexto());
            } catch (LeituraInvalidaException e) {
                utils.imprimirMensagem(e.getMessage() + "\n\n");
            }
        }
    }
}
