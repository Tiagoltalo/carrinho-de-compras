package br.com.trabalho.carrinhodecompras.controller;

import br.com.trabalho.carrinhodecompras.model.Login;
import br.com.trabalho.carrinhodecompras.model.Usuario;
import br.com.trabalho.carrinhodecompras.io.LeitorDeUsuario;
import br.com.trabalho.carrinhodecompras.io.MenuUsuario;
import br.com.trabalho.carrinhodecompras.io.MenuAdministrador;
import br.com.trabalho.carrinhodecompras.utils.LeitorDeArquivos;
import br.com.trabalho.carrinhodecompras.utils.CriadorDeArquivo;
import br.com.trabalho.carrinhodecompras.utils.Utils;

public class ControllerLogin {
    private final Login login = new Login();
    private final MenuUsuario menuUsuario = new MenuUsuario();
    private final MenuAdministrador menuAdministrador = new MenuAdministrador();
    private final LeitorDeUsuario leitorDeUsuario = new LeitorDeUsuario();
    private final LeitorDeArquivos leitorDeArquivos = new LeitorDeArquivos();
    private final CriadorDeArquivo criadorDeArquivo = new CriadorDeArquivo();
    private final Utils utils = new Utils();

    public void criarUsuario () {
        int id = Usuario.buscarUltimoId(Usuario.getUsuarios());
        String nome = leitorDeUsuario.lerNome();
        String senha = leitorDeUsuario.lerSenha();

        boolean usuarioExiste = Usuario.verificarExistenciaDeUsuario(Usuario.getUsuarios(), nome);

        if (!(usuarioExiste)) {
            Usuario usuario = new Usuario(id, nome, senha);
            Usuario.adicionarUsuarioALista(usuario);
            utils.imprimirMensagem("\nUsuário criado com sucesso.");
        } else {
            utils.imprimirMensagem("\nJá existe um usuário com esse nome. Por favor, insira um nome diferente.");
        }

        utils.pausar();
    }

    public void logarUsuario () {
        String nome = leitorDeUsuario.lerNome();
        String senha = leitorDeUsuario.lerSenha();
        Usuario usuarioLogado = login.validarLogin(nome, senha);

        if (usuarioLogado == null) {
            utils.imprimirMensagem("\nA senha ou nome são inválidos.");
            utils.pausar();
        } else if (usuarioLogado.getNome().equals("admin")) {
            utils.imprimirMensagem("\nVocê entrou como um administrador. Bem vindo.\n");
            utils. pausar();

            menuAdministrador.menu();
        } else {
            utils.imprimirMensagem("\nLogin efetuado com sucesso.\nBem vindo, " + usuarioLogado.getNome() + ".\n");
            utils.pausar();

            menuUsuario.menu();
        }
    }
}
