package br.com.trabalho.carrinhodecompras.io;

import java.math.BigDecimal;
import java.util.Locale;
import br.com.trabalho.carrinhodecompras.model.Produto;

public class UsuarioView {
    public void imprimirProduto (Produto produto) {
        imprimirProduto(produto.getId(), produto.getNome(), produto.getPreco(), produto.getQuantidade());
    }

    public void imprimirProduto (int id, String nome, BigDecimal preco, int quantidade) {
        imprimir(id, nome, preco, "Quantidade em estoque", quantidade);
    }

    public void imprimirItemDoCarrinho (int id, String nome, BigDecimal preco, int quantidade) {
        imprimir(id, nome, preco, "Quantidade no carrinho", quantidade);
    }

    private void imprimir (int id, String nome, BigDecimal preco, String rotuloDaQuantidade, int quantidade) {
        System.out.printf(Locale.forLanguageTag("pt-BR"), """
            
        Id: %d
        Nome: %s
        Preço: R$ %.2f
        %s: %d
        
        ══════════════════════════════════════
            """, id, nome, preco, rotuloDaQuantidade, quantidade);
    }
}
