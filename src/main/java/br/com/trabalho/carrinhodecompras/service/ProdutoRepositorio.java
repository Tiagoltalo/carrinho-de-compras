package br.com.trabalho.carrinhodecompras.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import br.com.trabalho.carrinhodecompras.exception.ProdutoDuplicadoException;
import br.com.trabalho.carrinhodecompras.exception.ProdutoNaoEncontradoException;
import br.com.trabalho.carrinhodecompras.model.Produto;
import br.com.trabalho.carrinhodecompras.model.ProdutoComum;
import br.com.trabalho.carrinhodecompras.model.ValidadorProduto;

public class ProdutoRepositorio extends RepositorioArquivo<Produto> {
    public static final String SEPARADOR = ";";
    public static final Path ARQUIVO_PADRAO = Paths.get("data", "produtos.txt");

    public ProdutoRepositorio () {
        this(ARQUIVO_PADRAO);
    }

    public ProdutoRepositorio (Path arquivo) {
        super(arquivo);
    }

    @Override
    protected Produto deLinha (String linha) {
        String[] campos = linha.split(SEPARADOR, -1);

        if (campos.length != 4) {
            throw new IllegalArgumentException("esperados 4 campos (id;nome;preco;quantidade), encontrados " + campos.length);
        }

        // Os mesmos validadores do cadastro: um arquivo editado à mão com dados inválidos é rejeitado.
        return new ProdutoComum(
            ValidadorProduto.validarId(campos[0]),
            campos[1],
            ValidadorProduto.validarPreco(campos[2]),
            ValidadorProduto.validarQuantidade(campos[3])
        );
    }

    @Override
    protected String paraLinha (Produto produto) {
        return String.join(SEPARADOR,
            String.valueOf(produto.getId()),
            produto.getNome(),
            produto.getPreco().toPlainString(),
            String.valueOf(produto.getQuantidade())
        );
    }

    public List<Produto> listarTodos () {
        List<Produto> produtos = lerTodos();
        produtos.sort(Comparator.comparingInt(Produto::getId));
        return produtos;
    }

    public Optional<Produto> buscarPorId (int id) {
        return lerTodos().stream().filter(p -> p.getId() == id).findFirst();
    }

    public boolean existe (int id) {
        return buscarPorId(id).isPresent();
    }

    /** Insere um novo produto; recusa se o id já existir. */
    public void inserir (Produto produto) {
        List<Produto> produtos = lerTodos();

        if (produtos.contains(produto)) { // equals() compara pelo id
            throw new ProdutoDuplicadoException(produto.getId());
        }

        produtos.add(produto);
        gravarTodos(produtos);
    }

    /** Substitui o produto de mesmo id pelos dados informados. */
    public void atualizar (Produto produto) {
        List<Produto> produtos = lerTodos();
        int posicao = produtos.indexOf(produto);

        if (posicao < 0) {
            throw new ProdutoNaoEncontradoException(produto.getId());
        }

        produtos.set(posicao, produto);
        gravarTodos(produtos);
    }

    public void remover (int id) {
        List<Produto> produtos = new ArrayList<>(lerTodos());

        if (!produtos.removeIf(p -> p.getId() == id)) {
            throw new ProdutoNaoEncontradoException(id);
        }

        gravarTodos(produtos);
    }
}
