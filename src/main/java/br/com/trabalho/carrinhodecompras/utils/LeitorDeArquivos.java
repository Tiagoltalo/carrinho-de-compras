package br.com.trabalho.carrinhodecompras.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;
import java.util.ArrayList;
import br.com.trabalho.carrinhodecompras.model.Usuario;

public class LeitorDeArquivos {
    private final Utils utils = new Utils();
    private final int CAMPOS_DE_USUARIOS = 3;
    private final int CAMPOS_DE_PRODUTOS = 1; // Rever o número de campos das constantes, a partir dessa.
    private final int CAMPOS_DE_COMPRAS = 1;
    private final int CAMPOS_DE_CARRINHO = 1;

    private List<String[]> lerArquivo (String nome, int numeroDeCampos) {
        Path path = Path.of("data", nome + ".txt");
        
        try (BufferedReader bufferDeLeitura = Files.newBufferedReader(path)) {
            String linha;
            String[] dados;
            List<String[]> dadosList = new ArrayList<>();

            while ((linha = bufferDeLeitura.readLine())!= null) {
                dados = linha.split(";");

                // Lançar aviso aqui e outro caso haja uma linha em branco
                if (dados.length != numeroDeCampos) {
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

    // Alterar a convenção de nomes de arquivos, como carrinho_nomeusuario.txt.
    public void lerProdutos (String nome) {
        List<String[]> produtos = lerArquivo(nome, CAMPOS_DE_PRODUTOS);

        for (String[] produto : produtos) {
            
        }
    }

    public void lerUsuarios (String nome) {
        List<String[]> usuarios = lerArquivo(nome, CAMPOS_DE_USUARIOS);

        for (String[] dadosUsuario : usuarios) {
            try {
                int idUsuario = Integer.parseInt(dadosUsuario[0]);
                String nomeUsuario = dadosUsuario[1];
                String senhaUsuario = dadosUsuario[2];
    
                Usuario usuario = new Usuario(idUsuario, nomeUsuario, senhaUsuario);
                Usuario.adicionarUsuarioALista(usuario);
            } catch (NumberFormatException e) {
                continue;
            }
        }
    }

    public void lerCarrinho (String nome) {
        List<String[]> itens = lerArquivo(nome, CAMPOS_DE_CARRINHO);

        for (String[] item : itens) {
            
        }
    }

    public void lerCompras (String nome) {
        List<String[]> compras = lerArquivo(nome, CAMPOS_DE_COMPRAS);

        for (String[] compra : compras) {
            
        }
    }
}
