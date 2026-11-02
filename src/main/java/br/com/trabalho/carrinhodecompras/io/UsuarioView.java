package br.com.trabalho.carrinhodecompras.io;

import java.util.Locale;

public class UsuarioView {
    public void imprimirProduto (int id, String nome, String preco, int quantidade) {
        System.out.printf(Locale.forLanguageTag("pt-BR"), """
            ══════════════════════════════════════
            
            Id: %d
            Nome: %s
            Preço: %s
            Quantidade em estoque: %d

            """, id, nome, preco, quantidade);
    }
}
