package br.com.trabalho.carrinhodecompras.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Compra {
    private static final Map<String, List<Compra>> historicoPorCliente = new HashMap<>();
    private static final Set<String> clientesCarregados = new HashSet<>();

    private final String id;
    private final String nomeDoCliente;
    private final List<ItemComprado> itens;
    private final String formaDePagamento;
    private final BigDecimal valorDaCompra;
    private final BigDecimal valorPago;
    private final String codigoDeAutorizacao;
    private final LocalDateTime dataHora;

    public Compra (String id, String nomeDoCliente, List<ItemComprado> itens, String formaDePagamento,
                   BigDecimal valorDaCompra, BigDecimal valorPago, String codigoDeAutorizacao, LocalDateTime dataHora) {
        this.id = id;
        this.nomeDoCliente = nomeDoCliente;
        this.itens = List.copyOf(itens); // cópia imutável
        this.formaDePagamento = formaDePagamento;
        this.valorDaCompra = valorDaCompra;
        this.valorPago = valorPago;
        this.codigoDeAutorizacao = codigoDeAutorizacao;
        this.dataHora = dataHora;
    }

    /** Cria uma compra nova a partir de um pagamento aprovado, com um id único. */
    public static Compra criar (String nomeDoCliente, List<ItemComprado> itens, ComprovanteDePagamento comprovante) {
        String id = UUID.randomUUID().toString().substring(0, 8);

        return new Compra(id, nomeDoCliente, itens, comprovante.formaDePagamento(), comprovante.valorDaCompra(),
                comprovante.valorPago(), comprovante.codigoDeAutorizacao(), comprovante.dataHora());
    }

    public static void adicionarCompraAoHistorico (Compra compra) {
        historicoPorCliente.computeIfAbsent(compra.getNomeDoCliente(), nome -> new ArrayList<>()).add(compra);
    }

    /** Histórico (em memória) do cliente, da compra mais antiga para a mais recente. */
    public static List<Compra> getHistorico (String nomeDoCliente) {
        return historicoPorCliente.computeIfAbsent(nomeDoCliente, nome -> new ArrayList<>());
    }

    public static Map<String, List<Compra>> getHistoricoPorCliente () { return historicoPorCliente; }

    /** Evita ler o arquivo do mesmo cliente duas vezes (o que duplicaria as compras ao logar de novo). */
    public static boolean marcarComoCarregado (String nomeDoCliente) {
        return clientesCarregados.add(nomeDoCliente);
    }

    public String getId () { return this.id; }

    public String getNomeDoCliente () { return this.nomeDoCliente; }

    public List<ItemComprado> getItens () { return this.itens; }

    public String getFormaDePagamento () { return this.formaDePagamento; }

    public BigDecimal getValorDaCompra () { return this.valorDaCompra; }

    public BigDecimal getValorPago () { return this.valorPago; }

    public String getCodigoDeAutorizacao () { return this.codigoDeAutorizacao; }

    public LocalDateTime getDataHora () { return this.dataHora; }
}
