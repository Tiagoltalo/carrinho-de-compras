package br.com.trabalho.carrinhodecompras.model;

import java.math.BigDecimal;

/**
 * Produto padrão da loja. Novos tipos (ex: perecível, eletrônico) podem ser
 * criados estendendo {@link Produto} sem alterar o restante do sistema.
 */
public class ProdutoComum extends Produto {
    public ProdutoComum (int id, String nome, BigDecimal preco, int quantidade) {
        super(id, nome, preco, quantidade);
    }

    @Override
    public String getCategoria () {
        return "Comum";
    }
}
