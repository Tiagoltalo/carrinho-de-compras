package br.com.trabalho.carrinhodecompras.controller;

import java.util.List;
import br.com.trabalho.carrinhodecompras.exception.ArmazenamentoException;
import br.com.trabalho.carrinhodecompras.exception.ProdutoException;
import br.com.trabalho.carrinhodecompras.io.LeitorDeProduto;
import br.com.trabalho.carrinhodecompras.model.Produto;
import br.com.trabalho.carrinhodecompras.model.ProdutoComum;
import br.com.trabalho.carrinhodecompras.service.ProdutoService;
import br.com.trabalho.carrinhodecompras.utils.Utils;

public class ControllerProduto {
    private final LeitorDeProduto leitorDeProduto = new LeitorDeProduto();
    private final ProdutoService produtoService;
    private final Utils utils = new Utils();

    public ControllerProduto () {
        this(new ProdutoService());
    }

    public ControllerProduto (ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    public void cadastrarProduto () {
        try {
            utils.imprimirMensagem("=== Cadastrar Produto ===\n\n");

            int id = lerIdNaoCadastrado();
            Produto produto = new ProdutoComum(
                id,
                leitorDeProduto.lerNome(),
                leitorDeProduto.lerPreco(),
                leitorDeProduto.lerQuantidade()
            );

            produtoService.cadastrar(produto); // checa duplicidade de novo, direto no arquivo
            utils.imprimirMensagem("\nProduto cadastrado com sucesso:\n" + produto + "\n");
        } catch (ProdutoException | ArmazenamentoException e) {
            utils.imprimirMensagem("\nErro: " + e.getMessage() + "\n");
        }
        utils.pausar();
    }

    public void consultarProduto () {
        try {
            utils.imprimirMensagem("=== Consultar Produto ===\n\n");
            Produto produto = produtoService.buscarPorId(leitorDeProduto.lerId());
            utils.imprimirMensagem("\n" + produto + "\n");
        } catch (ProdutoException | ArmazenamentoException e) {
            utils.imprimirMensagem("\nErro: " + e.getMessage() + "\n");
        }
        utils.pausar();
    }

    public void modificarQuantidade () {
        try {
            utils.imprimirMensagem("=== Modificar Quantidade em Estoque ===\n\n");
            Produto produto = produtoService.buscarPorId(leitorDeProduto.lerId());
            utils.imprimirMensagem("\nProduto: " + produto + "\n\n");

            produtoService.alterarQuantidade(produto.getId(), leitorDeProduto.lerQuantidade());
            utils.imprimirMensagem("\nEstoque atualizado com sucesso.\n");
        } catch (ProdutoException | ArmazenamentoException e) {
            utils.imprimirMensagem("\nErro: " + e.getMessage() + "\n");
        }
        utils.pausar();
    }

    public void excluirProduto () {
        try {
            utils.imprimirMensagem("=== Excluir Produto ===\n\n");
            Produto produto = produtoService.buscarPorId(leitorDeProduto.lerId());
            utils.imprimirMensagem("\nProduto: " + produto + "\n\n");

            if (leitorDeProduto.lerConfirmacao("Deseja realmente excluir este produto?")) {
                produtoService.excluir(produto.getId());
                utils.imprimirMensagem("\nProduto excluído com sucesso.\n");
            } else {
                utils.imprimirMensagem("\nExclusão cancelada.\n");
            }
        } catch (ProdutoException | ArmazenamentoException e) {
            utils.imprimirMensagem("\nErro: " + e.getMessage() + "\n");
        }
        utils.pausar();
    }

    public void listarProdutos () {
        try {
            utils.imprimirMensagem("=== Produtos Cadastrados ===\n\n");
            List<Produto> produtos = produtoService.listar();

            if (produtos.isEmpty()) {
                utils.imprimirMensagem("Nenhum produto cadastrado.\n");
            } else {
                for (Produto produto : produtos) {
                    utils.imprimirMensagem(produto + "\n");
                }
            }
        } catch (ArmazenamentoException e) {
            utils.imprimirMensagem("\nErro: " + e.getMessage() + "\n");
        }
        utils.pausar();
    }

    /** Pede o id até o usuário digitar um que ainda não exista. */
    private int lerIdNaoCadastrado () {
        while (true) {
            int id = leitorDeProduto.lerId();

            if (!produtoService.existe(id)) {
                return id;
            }
            utils.imprimirMensagem(String.format("Já existe um produto cadastrado com o id %d. Escolha outro id.\n\n", id));
        }
    }
}
