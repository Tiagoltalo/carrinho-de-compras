package br.com.trabalho.carrinhodecompras.io;

import br.com.trabalho.carrinhodecompras.utils.Utils;

public class LeitorDeOpcao {
    private final Utils utils = new Utils();

    public int lerOpcao() {
        while (true) {
            System.out.print("Digite a opção desejada: ");
            String texto = utils.lerTexto();

            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                utils.imprimirMensagem(
                    "Por favor, insira um valor válido.\n\n"
                );
            }
        }
    }
}