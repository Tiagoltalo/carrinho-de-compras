package br.com.trabalho.carrinhodecompras.io;

import br.com.trabalho.carrinhodecompras.controller.ControllerUsuario;
import br.com.trabalho.carrinhodecompras.model.Carrinho;
import br.com.trabalho.carrinhodecompras.model.Usuario;
import br.com.trabalho.carrinhodecompras.utils.LeitorDeArquivos;
import br.com.trabalho.carrinhodecompras.utils.EscritorDeArquivos;
import br.com.trabalho.carrinhodecompras.utils.Utils;

public class MenuUsuario {
    private final MenuView menuView = new MenuView();
    private final LeitorDeOpcao leitorDeOpcao = new LeitorDeOpcao();
    private final LeitorDeArquivos leitorDeArquivos = new LeitorDeArquivos();
    private final EscritorDeArquivos escritorDeArquivos = new EscritorDeArquivos();
    private final ControllerUsuario controllerUsuario = new ControllerUsuario();
    private final Utils utils = new Utils();

    public MenuUsuario () {}
    
    public void menu (Usuario usuario) {
        boolean looping = true;
        int opcao;

        Carrinho.limpar(); 
        leitorDeArquivos.lerCarrinho(usuario.getNome());
        leitorDeArquivos.lerCompras(usuario.getNome());

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
                    controllerUsuario.acessarCarrinho(usuario);
                    break;

                case 3:
                    controllerUsuario.imprimirHistoricoDeCompras(usuario);
                    break;

                case 0:
                    escritorDeArquivos.escreverCarrinho(usuario.getNome());
                    Carrinho.limpar();
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