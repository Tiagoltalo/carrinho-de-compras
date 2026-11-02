package br.com.trabalho.carrinhodecompras.controller;

import java.math.BigDecimal;
import br.com.trabalho.carrinhodecompras.exception.ProdutoDuplicadoException;
import br.com.trabalho.carrinhodecompras.io.LeitorDeOpcao;
import br.com.trabalho.carrinhodecompras.io.LeitorDeProduto;
import br.com.trabalho.carrinhodecompras.io.MenuView;
import br.com.trabalho.carrinhodecompras.model.Login;
import br.com.trabalho.carrinhodecompras.model.Produto;
import br.com.trabalho.carrinhodecompras.model.ProdutoComum;
import br.com.trabalho.carrinhodecompras.utils.Utils;

/**
 * Gerenciamento de produtos pelo administrador: cadastrar, consultar,
 * editar e excluir. Pede os dados ao usuário, altera a lista de produtos
 * em memória. O arquivo produtos.txt é gravado ao sair do programa (MenuPrincipal),
 * da mesma forma que os usuários.
 */
public class ControllerProduto {
    private static final int TENTATIVAS_DE_SENHA = 3;

    private final LeitorDeProduto leitorDeProduto = new LeitorDeProduto();
    private final LeitorDeOpcao leitorDeOpcao = new LeitorDeOpcao();
    private final MenuView menuView = new MenuView();
    private final Login login = new Login();
    private final Utils utils = new Utils();

    // ---------- CADASTRAR ----------

    public void cadastrarProduto () {
        // Mesmo esquema do id de usuário (ControllerLogin.criarUsuario): último id + 1.
        int id = Produto.buscarUltimoId(Produto.getProdutos()) + 1;

        utils.imprimirMensagem("=== Cadastrar Produto ===\n\n");
        String nome = leitorDeProduto.lerNome();
        BigDecimal preco = leitorDeProduto.lerPreco();
        int quantidade = leitorDeProduto.lerQuantidade();

        try {
            Produto produto = new ProdutoComum(id, nome, preco, quantidade);
            Produto.adicionarProdutoALista(produto); // recusa id duplicado
            utils.imprimirMensagem("\nProduto cadastrado com sucesso. O id gerado foi " + id + ".\n");
        } catch (ProdutoDuplicadoException e) {
            utils.imprimirMensagem("\n" + e.getMessage() + "\n");
        }

        utils.pausar();
    }

    // ---------- CONSULTAR ----------

    public void consultarProduto () {
        utils.imprimirMensagem("=== Consultar Produto ===\n\n");
        Produto produto = buscarProdutoPeloId();

        if (produto != null) {
            utils.imprimirMensagem("\n");
            imprimirDetalhes(produto);
        }

        utils.pausar();
    }

    // ---------- EDITAR ----------

    public void editarProduto () {
        utils.imprimirMensagem("=== Editar Produto ===\n\n");
        Produto produto = buscarProdutoPeloId();

        if (produto == null) {
            utils.pausar();
            return;
        }

        boolean looping = true;

        while (looping) {
            utils.limparTerminal();
            imprimirDetalhes(produto);
            utils.imprimirMensagem("\n");
            menuView.imprimirMenuEditarProduto();

            int opcao = leitorDeOpcao.lerOpcao();
            utils.imprimirMensagem("\n");

            switch (opcao) {
                case 1:
                    produto.setNome(leitorDeProduto.lerNome());
                            utils.imprimirMensagem("\nNome atualizado com sucesso.\n");
                    utils.pausar();
                    break;

                case 2:
                    produto.setQuantidade(leitorDeProduto.lerQuantidade());
                            utils.imprimirMensagem("\nQuantidade em estoque atualizada com sucesso.\n");
                    utils.pausar();
                    break;

                case 3:
                    produto.setPreco(leitorDeProduto.lerPreco());
                            utils.imprimirMensagem("\nPreço atualizado com sucesso.\n");
                    utils.pausar();
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

    // ---------- EXCLUIR ----------

    public void excluirProduto () {
        utils.imprimirMensagem("=== Excluir Produto ===\n\n");
        Produto produto = buscarProdutoPeloId();

        if (produto == null) {
            utils.pausar();
            return;
        }

        utils.imprimirMensagem(String.format("\nProduto: %s (id %d)\n", produto.getNome(), produto.getId()));

        if (!confirmarExclusao()) {
            utils.imprimirMensagem("\nExclusão cancelada.\n");
        } else if (!confirmarSenhaDoAdministrador()) {
            utils.imprimirMensagem("\nSenha incorreta. Exclusão cancelada.\n");
        } else {
            Produto.removerProdutoDaLista(produto);
            utils.imprimirMensagem("\nProduto excluído com sucesso.\n");
        }

        utils.pausar();
    }

    // ---------- auxiliares ----------

    /** Pede o id e devolve o produto; se não existir, avisa e devolve null. */
    private Produto buscarProdutoPeloId () {
        int id = leitorDeProduto.lerId();
        Produto produto = Produto.buscarPorId(Produto.getProdutos(), id);

        if (produto == null) {
            utils.imprimirMensagem("\nNenhum produto encontrado com o id " + id + ".\n");
        }

        return produto;
    }

    private void imprimirDetalhes (Produto produto) {
        utils.imprimirMensagem("ID: " + produto.getId() + "\n");
        utils.imprimirMensagem("Nome: " + produto.getNome() + "\n");
        utils.imprimirMensagem("Preço: " + produto.getPrecoFormatado() + "\n");
        utils.imprimirMensagem("Quantidade em estoque: " + produto.getQuantidade() + "\n");
    }

    /** Pergunta "1 - Sim / 2 - Não" até receber uma resposta válida. */
    private boolean confirmarExclusao () {
        while (true) {
            utils.imprimirMensagem("\n");
            menuView.imprimirMenuExcluirProduto();
            int opcao = leitorDeOpcao.lerOpcao();

            if (opcao == 1) return true;
            if (opcao == 2) return false;

            utils.imprimirMensagem("Insira 1 para sim ou 2 para não.\n\n");
        }
    }

    /** Pede a senha do administrador (até 3 tentativas), validando com a mesma regra do login. */
    private boolean confirmarSenhaDoAdministrador () {
        for (int tentativa = 1; tentativa <= TENTATIVAS_DE_SENHA; tentativa++) {
            System.out.print("\nDigite a senha do administrador para confirmar: ");
            String senha = utils.lerTexto().trim();

            if (login.validarLogin("admin", senha) != null) {
                return true;
            }

            if (tentativa < TENTATIVAS_DE_SENHA) {
                utils.imprimirMensagem("Senha incorreta. Tentativas restantes: " + (TENTATIVAS_DE_SENHA - tentativa) + ".\n");
            }
        }

        return false;
    }
}
