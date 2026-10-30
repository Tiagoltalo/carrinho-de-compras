package br.com.trabalho.carrinhodecompras.controller;

import br.com.trabalho.carrinhodecompras.io.LeitorDeUsuario;
import br.com.trabalho.carrinhodecompras.utils.Utils;

public class ControllerLogin {
    private final LeitorDeUsuario leitorDeUsuario = new LeitorDeUsuario();
    private final Utils utils = new Utils();

    public void criarUsuario () {
        leitorDeUsuario.lerNome();
        leitorDeUsuario.lerSenha();
        utils.imprimirMensagem("\nUsuário criado com sucesso.");
        utils.pausar();
    }
}
