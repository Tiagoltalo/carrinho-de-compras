package br.com.trabalho.carrinhodecompras.exception;

public class ProdutoNaoEncontradoException extends ProdutoException {
    private final int id;

    public ProdutoNaoEncontradoException (int id) {
        super(String.format("Nenhum produto encontrado com o id %d.", id));
        this.id = id;
    }

    public int getId () { return id; }
}
