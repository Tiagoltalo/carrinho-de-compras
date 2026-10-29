package br.com.trabalho.carrinhodecompras.io;

import br.com.trabalho.carrinhodecompras.utils.Utils;

public class MenuAdministrador {
    private final MenuView menuView = new MenuView();
    private final LeitorDeOpcao leitorDeOpcao = new LeitorDeOpcao();
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
                    System.out.print("Cadastrou o produto...");
                    break;

                case 2:
                    System.out.print("Consultou o produto...");
                    break;

                case 3:
                    System.out.print("Modificou a quantidade em estoque...");
                    break;

                case 4:
                    System.out.print("Excluiu o produto...");
                    break;

                case 5:
                    System.out.print("Retornou ao menu principal...");
                    looping = false;
                    break;
            
                default:
                    utils.imprimirMensagem("O valor inserido não condiz com nenhuma das opções.\nInsira um valor entre 1 - 5.\n");
                    utils.pausar();
                    break;
            }
        }
    }
}
