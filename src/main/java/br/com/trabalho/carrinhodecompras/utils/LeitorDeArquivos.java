package br.com.trabalho.carrinhodecompras.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;
import java.util.ArrayList;
import br.com.trabalho.carrinhodecompras.model.Usuario;

// Observação: fazer um overloading para ler diferentes arquivos: usuarios.txt, compras_nomeusuario.txt, carrinho_nomeusuario.txt e produtos.txt.
public class LeitorDeArquivos {
    private final Utils utils = new Utils();

    private List<String[]> lerArquivo (String nome) {
        Path path = Path.of("data", nome + ".txt");
        
        try (BufferedReader bufferDeLeitura = Files.newBufferedReader(path)) {
            String linha;
            String[] dados;
            List<String[]> dadosList = new ArrayList<>();

            while ((linha = bufferDeLeitura.readLine())!= null) {
                dados = linha.split(";");

                if (dados.length != 3) {
                    continue;
                }

                dadosList.add(dados);
            }

            return dadosList;
        } catch (IOException e) {
            utils.imprimirMensagem("Não foi possível ler o arquivo.");
            return new ArrayList<>();
        }
    }

    public void lerProdutos (String nome) {
        List<String[]> produtos = lerArquivo(nome);

        for (String[] produto : produtos) {

        }
    }

    public void lerUsuarios (String nome) {
        List<String[]> usuarios = lerArquivo(nome);

        for (String[] usuario : usuarios) {

        }
    }

    public void lerCarrinho (String nome) {
        List<String[]> itens = lerArquivo(nome);

        for (String[] item : itens) {
            
        }
    }

    public void lerCompras (String nome) {
        List<String[]> compras = lerArquivo(nome);

        for (String[] compra : compras) {
            
        }
    }
}
