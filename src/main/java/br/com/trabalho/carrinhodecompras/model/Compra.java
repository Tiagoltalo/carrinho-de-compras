package br.com.trabalho.carrinhodecompras.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Compra {
    private static final Map<String, List<Compra>> historicoPorCliente = new HashMap<>();
    private static final Set<String> clientesCarregados = new HashSet<>();

    private final int id;
    private final String nomeDoCliente;
    private final List<ItemComprado> itens;
    private final String formaDePagamento;
    private final BigDecimal valorDaCompra;
    private final BigDecimal valorPago;
    private final LocalDateTime dataHora;

    public Compra (int id, String nomeDoCliente, List<ItemComprado> itens, String formaDePagamento, BigDecimal valorDaCompra, BigDecimal valorPago, LocalDateTime dataHora) {
        this.id = id;
        this.nomeDoCliente = nomeDoCliente;
        this.itens = List.copyOf(itens); // cópia imutável
        this.formaDePagamento = formaDePagamento;
        this.valorDaCompra = valorDaCompra;
        this.valorPago = valorPago;
        this.dataHora = dataHora;
    }

    /** Cria uma compra nova a partir de um pagamento aprovado, com um id único. */
    public static Compra criar (String nomeDoCliente, List<ItemComprado> itens, ComprovanteDePagamento comprovante) {
        int id = buscarUltimoId(getHistorico(nomeDoCliente)) + 1;

        return new Compra(id, nomeDoCliente, itens, comprovante.formaDePagamento(), comprovante.valorDaCompra(),
                comprovante.valorPago(), comprovante.dataHora());
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

    public static int buscarUltimoId (List<Compra> compras) {
        int ultimoId = 0;

        for (Compra compra : compras) {
            if (ultimoId < compra.getId()) {
                ultimoId = compra.getId();
            }
        }

        return ultimoId;
    }

    public int getId () { return this.id; }

    public String getNomeDoCliente () { return this.nomeDoCliente; }

    public List<ItemComprado> getItens () { return this.itens; }

    public String getFormaDePagamento () { return this.formaDePagamento; }

    public BigDecimal getValorDaCompra () { return this.valorDaCompra; }

    public BigDecimal getValorPago () { return this.valorPago; }

    public LocalDateTime getDataHora () { return this.dataHora; }
}
