package br.com.trabalho.carrinhodecompras.exception;

public class ProdutoDuplicadoException extends ProdutoException {
    private final int id;

    public ProdutoDuplicadoException (int id) {
        super(String.format("Já existe um produto cadastrado com o id %d.", id));
        this.id = id;
    }

    public int getId () { return id; }
}
