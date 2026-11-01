package br.com.trabalho.carrinhodecompras.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;
import java.util.ArrayList;
import br.com.trabalho.carrinhodecompras.model.Usuario;
import br.com.trabalho.carrinhodecompras.exception.FalhaNoArquivoException;

// Observação: todos os arquivos são sobreescritos, fazer um backup dos arquivos é recomendado (ver isso futuramente)
public class EscritorDeArquivos {
    private final Utils utils = new Utils();

    private void escreverArquivo (String nome, List<String[]> listaDeDados) {
        List<String> linhas = new ArrayList<>();
        Path diretorio = Path.of("data");
        Path path;

        try {
            Files.createDirectories(diretorio);
        } catch (IOException e) {
            utils.imprimirMensagem("ERRO: algum erro aconteceu.\n\n");
        }

        path = diretorio.resolve(nome + ".txt");
        
        if (listaDeDados == null) {
            throw new FalhaNoArquivoException("escrita do arquivo", "não há nenhum dado a ser inserido no arquivo.");
        }
        
        for (String[] campos : listaDeDados) {
            try {
                for (String campo : campos) {
                    if (campo == null || campo.contains(";")) {
                        throw new IllegalArgumentException("campo inválido: " + campo);
                    }
                }
            } catch (IllegalArgumentException e) {
                utils.imprimirMensagem(e.getMessage() + "\n\n");
                continue;
            }

            linhas.add(String.join(";", campos));
        }

        try {
            Files.write(path, linhas);
        } catch (IOException e) {
            utils.imprimirMensagem("ERRO: o arquivo " + nome + ".txt falhou ao ser escrito.\n\n");
        }
    }

    public void escreverProdutos () {

    }

    public void escreverUsuarios () {
        List<String[]> listaDeUsuarios = new ArrayList<>();

        for (Usuario usuario : Usuario.getUsuarios()) {
            String[] campos = {String.valueOf(usuario.getId()), usuario.getNome(), usuario.getSenha()};
            listaDeUsuarios.add(campos);
        }

        escreverArquivo("usuarios", listaDeUsuarios);
    }

    public void escreverCarrinho () {

    }

    public void escreverCompras () {

    }
}
