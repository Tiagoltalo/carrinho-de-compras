package br.com.trabalho.carrinhodecompras.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// Observação: fazer um overloading para escrever diferentes arquivos: usuarios.txt, compras_nomeusuario.txt e produtos.txt.
public class CriadorDeArquivo {
    private final Utils utils = new Utils();
    private final String caminho = "/home/tiagoitalo/Documentos/Carrinho de Compras/src/main/java/br/com/trabalho/carrinhodecompras/files/";

    public void criarArquivo (String nome) {
        File arquivo = new File(caminho + nome + ".txt");

        try {
            if (arquivo.createNewFile()) {
                utils.imprimirMensagem("Arquivo criado com sucesso.");
            } else {
                utils.imprimirMensagem("Arquivo já existe.");
            }
        } catch (IOException e) {
            utils.imprimirMensagem("Ocorreu um erro na criação do arquivo.");
        }
    }

    public void escreverNoArquivo (String nome, String nomeUsuario, String senhaUsuario) {
        try (FileWriter arquivo = new FileWriter(caminho + nome + ".txt")) {
            arquivo.write(nomeUsuario + "\t" + senhaUsuario);
        } catch (IOException e) {
            utils.imprimirMensagem("Não é possível escrever no arquivo.");
        }
    }
}
