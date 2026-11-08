package br.com.trabalho.carrinhodecompras.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.AtomicMoveNotSupportedException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import br.com.trabalho.carrinhodecompras.model.Carrinho;
import br.com.trabalho.carrinhodecompras.model.Compra;
import br.com.trabalho.carrinhodecompras.model.ItemComprado;
import br.com.trabalho.carrinhodecompras.model.Produto;
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

        // Grava num arquivo temporário e só depois troca pelo definitivo: se der erro no meio,
        // o arquivo antigo continua intacto (em vez de ficar cortado pela metade).
        Path temporario = diretorio.resolve(nome + ".txt.tmp");

        try {
            Files.write(temporario, linhas);

            try {
                Files.move(temporario, path, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(temporario, path, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            utils.imprimirMensagem("ERRO: o arquivo " + nome + ".txt falhou ao ser escrito.\n\n");
        }
    }

    public void escreverProdutos () {
        List<String[]> listaDeProdutos = new ArrayList<>();

        for (Produto produto : Produto.getProdutos()) {
            String[] campos = {
                String.valueOf(produto.getId()),
                produto.getNome(),
                produto.getPreco().toPlainString(),
                String.valueOf(produto.getQuantidade())
            };

            listaDeProdutos.add(campos);
        }

        escreverArquivo("produtos", listaDeProdutos);
    }

    public void escreverUsuarios () {
        List<String[]> listaDeUsuarios = new ArrayList<>();

        for (Usuario usuario : Usuario.getUsuarios()) {
            String[] campos = {
                String.valueOf(usuario.getId()), 
                usuario.getNome(), 
                usuario.getSenha()
            };

            listaDeUsuarios.add(campos);
        }

        escreverArquivo("usuarios", listaDeUsuarios);
    }

    public void escreverCarrinho (String nome) {
        List<String[]> listaDeProdutosNoCarrinho = new ArrayList<>();

        for (Carrinho produtoNoCarrinho : Carrinho.getListaDeProdutosNoCarrinho()) {
            String[] campos = {
                String.valueOf(produtoNoCarrinho.getId()),
                String.valueOf(produtoNoCarrinho.getIdProduto()),
                produtoNoCarrinho.getNome(),
                String.valueOf(produtoNoCarrinho.getPreco()),
                String.valueOf(produtoNoCarrinho.getQuantidade())
            };

            listaDeProdutosNoCarrinho.add(campos);
        }

        escreverArquivo("carrinho_" + nome, listaDeProdutosNoCarrinho);
    }

    public void escreverCompras () {
        for (Map.Entry<String, List<Compra>> historico : Compra.getHistoricoPorCliente().entrySet()) {
            if (historico.getValue().isEmpty()) {
                continue;
            }

            List<String[]> linhas = new ArrayList<>();

            for (Compra compra : historico.getValue()) {
                for (ItemComprado item : compra.getItens()) {
                    String[] campos = {
                        String.valueOf(compra.getId()),
                        compra.getDataHora().toString(),
                        compra.getFormaDePagamento().replace(";", ","),
                        compra.getValorDaCompra().toPlainString(),
                        compra.getValorPago().toPlainString(),
                        String.valueOf(item.idProduto()),
                        item.nome().replace(";", ","), // o ";" separa os campos do arquivo
                        item.precoUnitario().toPlainString(),
                        String.valueOf(item.quantidade())
                    };

                    linhas.add(campos);
                }
            }

            escreverArquivo("historico-compra-" + historico.getKey(), linhas);
        }
    }
}
