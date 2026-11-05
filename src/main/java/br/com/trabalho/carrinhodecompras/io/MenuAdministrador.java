package br.com.trabalho.carrinhodecompras.io;

import br.com.trabalho.carrinhodecompras.controller.ControllerProduto;
import br.com.trabalho.carrinhodecompras.utils.Utils;

public class MenuAdministrador {
    private final MenuView menuView = new MenuView();
    private final LeitorDeOpcao leitorDeOpcao = new LeitorDeOpcao();
    private final ControllerProduto controllerProduto = new ControllerProduto();
    private final Utils utils = new Utils();

    public MenuAdministrador () {}

    public void menu () {
        boolean looping = true;
        int opcao;

        while (looping) {
            utils.limparTerminal();

            menuView.imprimirMenuAdministrador();
            opcao = leitorDeOpcao.lerOpcao();

            utils.limparTerminal();

            switch (opcao) {
                case 1:
                    controllerProduto.cadastrarProduto();
                    break;

                case 2:
                    controllerProduto.consultarProduto();
                    break;

                case 3:
                    controllerProduto.editarProduto();
                    break;

                case 4:
                    controllerProduto.excluirProduto();
                    break;

                case 0:
                    looping = false;
                    break;
            
                default:
                    utils.imprimirMensagem("O valor inserido não condiz com nenhuma das opções.\nInsira um valor entre 0 - 4.\n");
                    utils.pausar();
                    break;
            }
        }
    }
}
