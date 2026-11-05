package br.com.trabalho.carrinhodecompras.utils;

import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Utils {
    Scanner scanner = new Scanner(System.in);

    public boolean verificarExistenciaDeArquivo (String nome) {
        File arquivo = new File("src/main/java/br/com/trabalho/carrinhodecompras/" + nome + ".txt");
        boolean arquivoExiste = arquivo.exists();

        return arquivoExiste;
    }

    public String lerTexto () {
        String texto = scanner.nextLine();

        return texto;
    }

    /** Formata um valor em reais, ex: "R$ 19,90". */
    public String formatarMoeda (BigDecimal valor) {
        return NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR")).format(valor);
    }

    public void imprimirMensagem (String mensagem) {
        System.out.print(mensagem);
    }

    public void pausar () {
        System.out.print("\nPressione Enter...");
        scanner.nextLine();
    }

    public void limparTerminal () {
        System.out.print("\033c");
    }
}
