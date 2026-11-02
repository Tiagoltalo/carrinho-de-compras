package br.com.trabalho.carrinhodecompras.io;

public class MenuView {
    public void imprimirMenuPrincipal () {
        System.out.println("""
        ╔══════════════════════════════════════╗
        ║              Menu Login              ║
        ╠══════════════════════════════════════╣
        ║  [ 1 ] Criar Usuário                 ║
        ║  [ 2 ] Entrar na Conta               ║
        ║  [ 0 ] Sair                          ║
        ╚══════════════════════════════════════╝

        Obs: Digite o número correspondente à opção.
        """);
    }
    
    public void imprimirMenuUsuario () {
        System.out.println("""
        ╔══════════════════════════════════════╗
        ║            Menu Principal            ║
        ╠══════════════════════════════════════╣
        ║  [ 1 ] Listar Produtos               ║
        ║  [ 2 ] Acessar Carrinho              ║
        ║  [ 3 ] Histórico de Compras          ║
        ║  [ 0 ] Retornar ao Menu Principal    ║
        ╚══════════════════════════════════════╝
        """);
    }


    public void imprimirMenuAdministrador () {
        System.out.println("""
        ╔══════════════════════════════════════════════════╗
        ║                  Menu Administrador              ║
        ╠══════════════════════════════════════════════════╣
        ║  [ 1 ] Cadastrar Produto                         ║
        ║  [ 2 ] Consultar Produto                         ║
        ║  [ 3 ] Editar Produto                            ║
        ║  [ 4 ] Excluir Produto                           ║
        ║  [ 0 ] Retornar ao Menu Principal                ║
        ╚══════════════════════════════════════════════════╝
        """);
    }

    public void imprimirMenuExcluirProduto () {
        System.out.println("""
        ╔══════════════════════════════════════════════════╗
        ║                  Excluir Produto                 ║
        ╠══════════════════════════════════════════════════╣
        ║  Deseja realmente excluir este produto?          ║
        ║                                                  ║
        ║  [ 1 ] Sim                                       ║
        ║  [ 2 ] Não                                       ║
        ╚══════════════════════════════════════════════════╝
        """);
    }

    public void imprimirMenuEditarProduto () {
        System.out.println("""
        ╔══════════════════════════════════════════════════╗
        ║                  Editar Produto                  ║
        ╠══════════════════════════════════════════════════╣
        ║  [ 1 ] Editar Nome                               ║
        ║  [ 2 ] Editar Quantidade em Estoque              ║
        ║  [ 3 ] Editar Preço                              ║
        ║  [ 0 ] Voltar ao Menu Administrador              ║
        ╚══════════════════════════════════════════════════╝
        """);
    }
}