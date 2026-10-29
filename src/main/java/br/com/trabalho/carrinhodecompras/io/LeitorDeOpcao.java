package br.com.trabalho.carrinhodecompras.io;

import br.com.trabalho.carrinhodecompras.utils.Utils;

public class LeitorDeOpcao {
    private final Utils utils = new Utils();

    public int lerOpcao (){
        while (true) {
            String texto;
            int numero;
    
            System.out.print("Digite a opção desejada: ");
            texto = utils.lerTexto();
    
            try {
                return numero = Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                utils.imprimirMensagem("Por favor, insira um valor válido.\n\n");
            };
        }
    }
}
