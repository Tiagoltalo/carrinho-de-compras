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

            System.out.print("Digite o nome do usuário (ex: tiagoitalo): ");
            texto = utils.lerTexto();
            texto = texto.trim();

            try {
                if (texto.equals("")) {
                    throw new LeituraInvalidaException(texto, "nome", "o campo não pode estar vazio.");
                } else if (texto.length() < 5) {
                    throw new LeituraInvalidaException(texto, "nome", "o campo não pode ter menos que 5 caracteres.");
                } else if (!(validarNome(texto))) {
                    throw new LeituraInvalidaException(texto, "nome", "o campo não pode conter números ou espaços.");
                } else {
                    return texto.toLowerCase();
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
            texto = texto.trim();

            try {
                if (texto.equals("")) {
                    throw new LeituraInvalidaException(texto, "senha", "o campo não pode estar vazio.");
                } else if (texto.length() < 5) {
                    throw new LeituraInvalidaException(texto, "senha", "o campo não pode ter menos que 5 caracteres.");
                } else if (!(validarSenha(texto))) {
                    throw new LeituraInvalidaException(texto, "senha", "o campo precisa conter números, caracteres maiúsculos e minúsculos e não pode conter espaços.");
                } else {
                    return texto;
                }
            } catch (LeituraInvalidaException e) {
                utils.imprimirMensagem(e.getMessage() + "\n\n");
            }

        }
    }

    private boolean validarNome (String nome) {
        Pattern patternNumero = Pattern.compile("[0-9]+");
        Pattern patternEspaco = Pattern.compile(" ");
        Matcher nomeNumero = patternNumero.matcher(nome);
        Matcher nomeEspaco = patternEspaco.matcher(nome);
        boolean nomeValido = false;

        if (!(nomeNumero.find()) && !(nomeEspaco.find())) {
            nomeValido = true;
        }

        return nomeValido;
    }

    private boolean validarSenha (String senha) {
        Pattern patternMinuscula = Pattern.compile("[a-z]+");
        Pattern patternMaiuscula = Pattern.compile("[A-Z]+");
        Pattern patternNumero = Pattern.compile("[0-9]+");
        Pattern patternEspaco = Pattern.compile(" ");
        Matcher senhaMinuscula = patternMinuscula.matcher(senha);
        Matcher senhaMaiuscula = patternMaiuscula.matcher(senha);
        Matcher senhaNumero = patternNumero.matcher(senha);
        Matcher senhaEspaco = patternEspaco.matcher(senha);
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

        if(senhaEspaco.find()) {
            senhaValida = false;
        }

        return senhaValida;
    }
}
