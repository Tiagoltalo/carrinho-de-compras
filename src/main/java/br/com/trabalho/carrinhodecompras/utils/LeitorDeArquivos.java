package br.com.trabalho.carrinhodecompras.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;
import java.util.ArrayList;
import br.com.trabalho.carrinhodecompras.model.Produto;
import br.com.trabalho.carrinhodecompras.model.ProdutoComum;
import br.com.trabalho.carrinhodecompras.model.Usuario;
import br.com.trabalho.carrinhodecompras.model.ValidadorProduto;
import br.com.trabalho.carrinhodecompras.exception.LeituraInvalidaException;
import br.com.trabalho.carrinhodecompras.exception.ProdutoDuplicadoException;

public class LeitorDeArquivos {
    private final Utils utils = new Utils();
    private final int CAMPOS_DE_USUARIOS = 3;
    private final int CAMPOS_DE_PRODUTOS = 4; // id;nome;preco;quantidade. Rever o número de campos das constantes seguintes.
    private final int CAMPOS_DE_COMPRAS = 1;
    private final int CAMPOS_DE_CARRINHO = 1;

    private List<String[]> lerArquivo (String nome, int numeroDeCampos) {
        Path path = Path.of("data", nome + ".txt");

        // Arquivo ainda não criado (ex: primeira execução): não é erro, só não há dados.
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

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
        boolean houveLinhaIgnorada = false;

        for (String[] dadosProduto : produtos) {
            try {
                // Usa as mesmas validações do cadastro: um arquivo editado à mão com dados inválidos é recusado.
                int idProduto = ValidadorProduto.validarId(dadosProduto[0]);
                String nomeProduto = dadosProduto[1];
                var precoProduto = ValidadorProduto.validarPreco(dadosProduto[2]);
                int quantidadeProduto = ValidadorProduto.validarQuantidade(dadosProduto[3]);

                Produto produto = new ProdutoComum(idProduto, nomeProduto, precoProduto, quantidadeProduto);
                Produto.adicionarProdutoALista(produto);
            } catch (LeituraInvalidaException | ProdutoDuplicadoException e) {
                utils.imprimirMensagem("Aviso: linha ignorada em " + nome + ".txt (" + String.join(";", dadosProduto) + "): " + e.getMessage() + "\n");
                houveLinhaIgnorada = true;
            }
        }

        if (houveLinhaIgnorada) {
            utils.pausar();
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
