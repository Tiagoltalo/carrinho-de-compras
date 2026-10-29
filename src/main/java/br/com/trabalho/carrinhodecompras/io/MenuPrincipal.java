package br.com.trabalho.carrinhodecompras.io;

import br.com.trabalho.carrinhodecompras.controller.ControllerLogin;
import br.com.trabalho.carrinhodecompras.utils.Utils;

public class MenuPrincipal {
    private final MenuView menuView = new MenuView();
    private final LeitorDeOpcao leitorDeOpcao = new LeitorDeOpcao();
    private final Utils utils = new Utils();

    public MenuPrincipal () {}

    public void menu () {
        boolean looping = true;
        int opcao;

        while (looping) {
            utils.limparTerminal();

            menuView.imprimirMenuPrincipal();
            opcao = leitorDeOpcao.lerOpcao();

            utils.limparTerminal();

            switch (opcao) {
                case 1:
                    System.out.print("Criou usuário...");
                    break;

                case 2:
                    System.out.print("Logou na conta...");
                    break;

                case 3:
                    System.out.print("Saiu do programa...");
                    looping = false;
                    break;
            
                default:
                    utils.imprimirMensagem("O valor inserido não condiz com nenhuma das opções.\nInsira um valor entre 1 - 3.\n");
                    utils.pausar();
                    break;
            }
        }
    }
}
