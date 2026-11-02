package br.com.trabalho.carrinhodecompras.io;

import java.math.BigDecimal;
import br.com.trabalho.carrinhodecompras.exception.LeituraInvalidaException;
import br.com.trabalho.carrinhodecompras.model.ValidadorProduto;
import br.com.trabalho.carrinhodecompras.utils.Utils;

/**
 * Lê do teclado cada campo de um produto, repetindo a pergunta até o usuário
 * digitar um valor válido (mesmo estilo do {@link LeitorDeUsuario}).
 * A validação em si é feita por {@link ValidadorProduto}.
 */
public class LeitorDeProduto {
    private final Utils utils = new Utils();

    public int lerId () {
        while (true) {
            System.out.print("Digite o id do produto: ");
            try {
                return ValidadorProduto.validarId(utils.lerTexto());
            } catch (LeituraInvalidaException e) {
                utils.imprimirMensagem(e.getMessage() + "\n\n");
            }
        }
    }

    public String lerNome () {
        while (true) {
            System.out.print("Digite o nome do produto: ");
            try {
                return ValidadorProduto.validarNome(utils.lerTexto());
            } catch (LeituraInvalidaException e) {
                utils.imprimirMensagem(e.getMessage() + "\n\n");
            }
        }
    }

    public BigDecimal lerPreco () {
        while (true) {
            System.out.print("Digite o preço do produto (ex: 19,90): ");
            try {
                return ValidadorProduto.validarPreco(utils.lerTexto());
            } catch (LeituraInvalidaException e) {
                utils.imprimirMensagem(e.getMessage() + "\n\n");
            }
        }
    }

    public int lerQuantidade () {
        while (true) {
            System.out.print("Digite a quantidade em estoque (0 ou mais): ");
            try {
                return ValidadorProduto.validarQuantidade(utils.lerTexto());
            } catch (LeituraInvalidaException e) {
                utils.imprimirMensagem(e.getMessage() + "\n\n");
            }
        }
    }
}
