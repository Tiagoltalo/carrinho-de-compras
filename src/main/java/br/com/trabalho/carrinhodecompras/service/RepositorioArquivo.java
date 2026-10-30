package br.com.trabalho.carrinhodecompras.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import br.com.trabalho.carrinhodecompras.exception.ArmazenamentoException;

public abstract class RepositorioArquivo<T> {
    private final Path arquivo;

    protected RepositorioArquivo (Path arquivo) {
        this.arquivo = arquivo;
    }

    /** Converte uma linha do arquivo em objeto; deve lançar exceção se a linha for inválida. */
    protected abstract T deLinha (String linha);

    /** Converte um objeto em uma linha do arquivo. */
    protected abstract String paraLinha (T entidade);

    public Path getArquivo () { return arquivo; }

    /** Lê todas as entidades do arquivo. Se o arquivo ainda não existe, devolve lista vazia. */
    protected List<T> lerTodos () {
        List<T> entidades = new ArrayList<>();

        if (!Files.exists(arquivo)) {
            return entidades;
        }

        try {
            List<String> linhas = Files.readAllLines(arquivo, StandardCharsets.UTF_8);

            for (int i = 0; i < linhas.size(); i++) {
                String linha = linhas.get(i);

                if (linha.isBlank() || linha.startsWith("#")) {
                    continue; // ignora linhas em branco e comentários
                }

                try {
                    entidades.add(deLinha(linha));
                } catch (RuntimeException e) {
                    throw new ArmazenamentoException(
                        String.format("Arquivo %s corrompido na linha %d (\"%s\"): %s",
                                arquivo, i + 1, linha, e.getMessage()), e);
                }
            }
        } catch (IOException e) {
            throw new ArmazenamentoException("Não foi possível ler o arquivo " + arquivo + ".", e);
        }

        return entidades;
    }

    protected void gravarTodos (List<T> entidades) {
        List<String> linhas = new ArrayList<>();
        for (T entidade : entidades) {
            linhas.add(paraLinha(entidade));
        }

        try {
            Path pasta = arquivo.toAbsolutePath().getParent();
            if (pasta != null) {
                Files.createDirectories(pasta);
            }

            Path temporario = Files.createTempFile(pasta, "dados-", ".tmp");
            try {
                Files.write(temporario, linhas, StandardCharsets.UTF_8);
                try {
                    Files.move(temporario, arquivo, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
                } catch (AtomicMoveNotSupportedException e) {
                    Files.move(temporario, arquivo, StandardCopyOption.REPLACE_EXISTING);
                }
            } finally {
                Files.deleteIfExists(temporario);
            }
        } catch (IOException e) {
            throw new ArmazenamentoException("Não foi possível gravar o arquivo " + arquivo + ".", e);
        }
    }
}
