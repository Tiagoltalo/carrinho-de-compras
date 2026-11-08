package br.com.trabalho.carrinhodecompras.io;

import java.math.BigDecimal;
import java.util.Locale;

public class UsuarioView {
    public void imprimirProduto (int id, String nome, BigDecimal preco, int quantidade) {
        System.out.printf(Locale.forLanguageTag("pt-BR"), """
            ══════════════════════════════════════
            
            Id: %d
            Nome: %s
            Preço: R$ %.2f
            Quantidade em estoque: %d

            ══════════════════════════════════════
            """, id, nome, preco, quantidade);
    }
}
