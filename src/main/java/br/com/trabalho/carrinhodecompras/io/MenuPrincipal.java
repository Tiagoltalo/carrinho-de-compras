package br.com.trabalho.carrinhodecompras.io;

import br.com.trabalho.carrinhodecompras.controller.ControllerLogin;
import br.com.trabalho.carrinhodecompras.utils.LeitorDeArquivos;
import br.com.trabalho.carrinhodecompras.utils.EscritorDeArquivos;
import br.com.trabalho.carrinhodecompras.utils.Utils;

public class MenuPrincipal {
    private final MenuView menuView = new MenuView();
    private final LeitorDeOpcao leitorDeOpcao = new LeitorDeOpcao();
    private final ControllerLogin controllerLogin = new ControllerLogin();
    private final LeitorDeArquivos leitorDeArquivos = new LeitorDeArquivos();
    private final EscritorDeArquivos escritorDeArquivos = new EscritorDeArquivos();
    private final Utils utils = new Utils();

    public MenuPrincipal () {}

    public void menu () {
        boolean looping = true;
        int opcao;

        leitorDeArquivos.lerUsuarios();
        leitorDeArquivos.lerProdutos();

        while (looping) {
            utils.limparTerminal();

            menuView.imprimirMenuPrincipal();
            opcao = leitorDeOpcao.lerOpcao();

            utils.limparTerminal();

            switch (opcao) {
                case 1:
                    controllerLogin.criarUsuario();
                    break;

                case 2:
                    controllerLogin.logarUsuario();
                    break;

                case 0:
                    escritorDeArquivos.escreverUsuarios();
                    escritorDeArquivos.escreverCompras(); 
                    escritorDeArquivos.escreverProdutos();
                    System.out.print("Saiu do programa...");
                    looping = false;
                    break;
            
                default:
                    utils.imprimirMensagem("O valor inserido não condiz com nenhuma das opções.\nInsira um valor entre 0 - 2.\n");
                    utils.pausar();
                    break;
            }
        }
    }
}