package br.com.trabalho.carrinhodecompras.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;
import java.util.ArrayList;

// Observação: todos os arquivos são sobreescritos, fazer um backup dos arquivos é recomendado (ver isso futuramente)
public class CriadorDeArquivo {
    private final Utils utils = new Utils();

    private void escreverArquivo (String nome, List<String[]> listaDeDados) {
        Path diretorio = Path.of("data");

        try {
            Files.createDirectories(diretorio);
        } catch (IOException e) {
            
        }
        
        Path path = diretorio.resolve(nome + ".txt");

        try (BufferedWriter bufferDeEscrita = Files.newBufferedWriter(path)) {
            for (String[] dados : listaDeDados) {
                
            }
        } catch (IOException e) {

        }
    }

    public void escreverProdutos () {

    }

    public void escreverUsuarios () {

    }

    public void escreverCarrinho () {

    }

    public void escreverCompras () {

    }
}
