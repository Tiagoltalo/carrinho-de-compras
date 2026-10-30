package br.com.trabalho.carrinhodecompras.utils;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import br.com.trabalho.carrinhodecompras.model.Usuario;

// Observação: fazer um overloading para ler diferentes arquivos: usuarios.txt, compras_nomeusuario.txt e produtos.txt.
public class LeitorDeArquivos {
    private final CriadorDeArquivo criadorDeArquivo = new CriadorDeArquivo();
    private final Utils utils = new Utils();
    private final String caminho = "/home/tiagoitalo/Documentos/Carrinho de Compras/src/main/java/br/com/trabalho/carrinhodecompras/files/";

    public void lerArquivo (String nome) {
        String[] dados;
        String linha;

        try (FileReader arquivo = new FileReader(caminho + nome + ".txt");) {
            BufferedReader bufferDeLeitura = new BufferedReader(arquivo);

            // Fazer melhorias sugeridas pelo Cloud
            while ((linha = bufferDeLeitura.readLine())!= null) {
                dados = linha.split("-");
                utils.imprimirMensagem(dados[0]);
                utils.imprimirMensagem(dados[1]);
                utils.imprimirMensagem(dados[2]);
                utils.pausar();
                Usuario usuario = new Usuario(Integer.parseInt(dados[0]), dados[1], dados[2]);
                Usuario.adicionarUsuarioALista(usuario);
            }
        } catch (IOException e) {
            utils.imprimirMensagem("Não foi possível ler o arquivo.");
        }
    }
}
