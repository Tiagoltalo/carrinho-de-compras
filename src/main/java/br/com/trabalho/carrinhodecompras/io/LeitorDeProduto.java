package br.com.trabalho.carrinhodecompras.io;

import java.math.BigDecimal;
import br.com.trabalho.carrinhodecompras.exception.LeituraInvalidaException;
import br.com.trabalho.carrinhodecompras.model.ValidadorProduto;
import br.com.trabalho.carrinhodecompras.utils.Utils;

public class LeitorDeProduto {
    private final Utils utils = new Utils();

    public int lerId () {
        while (true) {
            System.out.print("Digite o id do produto (número inteiro positivo): ");
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

    public boolean lerConfirmacao (String pergunta) {
        while (true) {
            System.out.print(pergunta + " (s/n): ");
            String resposta = utils.lerTexto().trim().toLowerCase();

            if (resposta.equals("s") || resposta.equals("sim")) return true;
            if (resposta.equals("n") || resposta.equals("nao") || resposta.equals("não")) return false;

            utils.imprimirMensagem("Responda com 's' ou 'n'.\n\n");
        }
    }
}
