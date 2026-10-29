package br.com.trabalho.carrinhodecompras.io;

import br.com.trabalho.carrinhodecompras.utils.Utils;

public class MenuUsuario {
    private final MenuView menuView = new MenuView();
    private final LeitorDeOpcao leitorDeOpcao = new LeitorDeOpcao();
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
                    System.out.print("Produtos...");
                    break;

                case 2:
                    System.out.print("Carrinho...");
                    break;

                case 3:
                    System.out.print("Histórico de Compras...");
                    break;

                case 0:
                    System.out.print("Retornando a tela de login...");
                    break;
            
                default:
                    utils.imprimirMensagem("O valor inserido não condiz com nenhuma das opções.\nInsira um valor entre 1 - 4.\n");
                    utils.pausar();
                    break;
            }
        }
    }
}