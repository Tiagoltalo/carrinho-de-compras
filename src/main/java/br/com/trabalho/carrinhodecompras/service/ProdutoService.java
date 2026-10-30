package br.com.trabalho.carrinhodecompras.service;

import java.util.List;
import br.com.trabalho.carrinhodecompras.exception.ProdutoNaoEncontradoException;
import br.com.trabalho.carrinhodecompras.model.Produto;

public class ProdutoService {
    private final ProdutoRepositorio repositorio;

    public ProdutoService () {
        this(new ProdutoRepositorio());
    }

    public ProdutoService (ProdutoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void cadastrar (Produto produto) {
        repositorio.inserir(produto);
    }

    public boolean existe (int id) {
        return repositorio.existe(id);
    }

    public Produto buscarPorId (int id) {
        return repositorio.buscarPorId(id).orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    public List<Produto> listar () {
        return repositorio.listarTodos();
    }

    public void alterarQuantidade (int id, int novaQuantidade) {
        Produto produto = buscarPorId(id);
        produto.setQuantidade(novaQuantidade); // valida (não aceita negativo)
        repositorio.atualizar(produto);
    }

    public void excluir (int id) {
        repositorio.remover(id);
    }
}
