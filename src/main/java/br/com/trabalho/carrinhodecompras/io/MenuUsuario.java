package br.com.trabalho.carrinhodecompras.io;

import br.com.trabalho.carrinhodecompras.controller.ControllerUsuario;
import br.com.trabalho.carrinhodecompras.utils.Utils;

public class MenuUsuario {
    private final MenuView menuView = new MenuView();
    private final LeitorDeOpcao leitorDeOpcao = new LeitorDeOpcao();
    private final ControllerUsuario controllerUsuario = new ControllerUsuario();
    private final Utils utils = new Utils();

    public MenuUsuario () {}
    
    public void menu () {
        boolean looping = true;
        int opcao;

        while (looping) {
            utils.limparTerminal();

            menuView.imprimirMenuUsuario();
            opcao = leitorDeOpcao.lerOpcao();

            utils.limparTerminal();

            switch (opcao) {
                case 1:
                    controllerUsuario.listarProdutos();
                    break;

                case 2:
                    controllerUsuario.acessarCarrinho();
                    break;

                case 3:
                    System.out.print("Histórico de Compras...");
                    break;

                case 0:
                    looping = false;
                    break;
            
                default:
                    utils.imprimirMensagem("O valor inserido não condiz com nenhuma das opções.\nInsira um valor entre 0 - 3.\n");
                    utils.pausar();
                    break;
            }
        }
    }
}