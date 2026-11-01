package br.com.trabalho.carrinhodecompras.controller;

import br.com.trabalho.carrinhodecompras.io.UsuarioView;
import br.com.trabalho.carrinhodecompras.utils.Utils;

public class ControllerUsuario {
    private final UsuarioView usuarioView = new UsuarioView();
    private final Utils utils = new Utils();

    public void listarProdutos () {
        for (int i = 0; i < 5; i++) {
            usuarioView.imprimirProduto(i, "produto", i, i);
        }

        utils.imprimirMensagem("══════════════════════════════════════\n\n");

        utils.imprimirMensagem("1: adicionar produto ao carrinho | 0: retornar ao menu");
        utils.pausar();
    }

    public void acessarCarrinho () {

    }

    public void imprimirHistoricoDeCompras () {

    }
}
