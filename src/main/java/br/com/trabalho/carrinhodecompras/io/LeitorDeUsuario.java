package br.com.trabalho.carrinhodecompras.io;

import java.util.regex.Pattern; // Define o padrão da expressão regular
import java.util.regex.Matcher; // Procura a expressão regular em um texto
import br.com.trabalho.carrinhodecompras.utils.Utils;
import br.com.trabalho.carrinhodecompras.exception.LeituraInvalidaException;;

public class LeitorDeUsuario {
    private final Utils utils = new Utils();

    public String lerNome () {
        while (true) {
            String texto;

            System.out.print("Digite o nome do usuário (ex: tiago italo): ");
            texto = utils.lerTexto();
            try {
                if (texto.equals("")) {
                    throw new LeituraInvalidaException(texto, "nome", "o campo não pode estar vazio");
                } else if (texto.length() < 5) {
                    throw new LeituraInvalidaException(texto, "nome", "o campo não pode ter menos que 5 caracteres");
                } else {
                    return texto;
                }
            } catch (LeituraInvalidaException e) {
                utils.imprimirMensagem(e.getMessage() + "\n\n");
            }
        }
    }

    public String lerSenha () {
        while (true) {
            String texto;

            System.out.print("Digite a senha: ");
            texto = utils.lerTexto();

            try {
                if (texto.equals("")) {
                    throw new LeituraInvalidaException(texto, "senha", "o campo não pode estar vazio");
                } else if (texto.length() < 5) {
                    throw new LeituraInvalidaException(texto, "senha", "o campo não pode ter menos que 5 caracteres");
                } else if (!(validarSenha(texto))) {
                    throw new LeituraInvalidaException(texto, "senha", "o campo precisa conter números e caracteres maiúsculos e minúsculos");
                } else {
                    return texto;
                }
            } catch (LeituraInvalidaException e) {
                utils.imprimirMensagem(e.getMessage() + "\n\n");
            }

        }
    }

    private boolean validarSenha (String senha) {
        Pattern patternMinuscula = Pattern.compile("[a-z]+");
        Pattern patternMaiuscula = Pattern.compile("[A-Z]+");
        Pattern patternNumero = Pattern.compile("[0-9]+");
        Matcher senhaMinuscula = patternMinuscula.matcher(senha);
        Matcher senhaMaiuscula = patternMaiuscula.matcher(senha);
        Matcher senhaNumero = patternNumero.matcher(senha);
        boolean senhaValida = true;

        if (!(senhaMinuscula.find())) {
            senhaValida = false;
        }

        if (!(senhaMaiuscula.find())) {
            senhaValida = false;
        }

        if (!(senhaNumero.find())) {
            senhaValida = false;
        }

        return senhaValida;
    }
}
