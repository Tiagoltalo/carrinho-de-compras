package br.com.trabalho.carrinhodecompras.controller;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import br.com.trabalho.carrinhodecompras.exception.PagamentoRecusadoException;
import br.com.trabalho.carrinhodecompras.io.LeitorDeOpcao;
import br.com.trabalho.carrinhodecompras.io.LeitorDePagamento;
import br.com.trabalho.carrinhodecompras.io.MenuView;
import br.com.trabalho.carrinhodecompras.model.ComprovanteDePagamento;
import br.com.trabalho.carrinhodecompras.model.FormaDePagamento;
import br.com.trabalho.carrinhodecompras.model.Pix;
import br.com.trabalho.carrinhodecompras.utils.Utils;

/**
 * Ponto de entrada do pagamento para o resto do sistema (ex: o carrinho).
 *
 * Uso:
 * <pre>
 *     Optional&lt;ComprovanteDePagamento&gt; resultado = controllerPagamento.realizarPagamento(totalDoCarrinho);
 *
 *     if (resultado.isPresent()) {
 *         // pago: dar baixa no estoque, limpar o carrinho, guardar no histórico...
 *     }
 * </pre>
 *
 * Quem chama só informa o valor da compra: este controller mostra as formas de
 * pagamento, lê os dados, mostra o valor com desconto, pede confirmação e
 * processa. Ele trabalha apenas com o tipo abstrato {@link FormaDePagamento},
 * sem if/switch para saber se é crédito, débito ou Pix.
 */
public class ControllerPagamento {
    private final LeitorDePagamento leitorDePagamento = new LeitorDePagamento();
    private final LeitorDeOpcao leitorDeOpcao = new LeitorDeOpcao();
    private final MenuView menuView = new MenuView();
    private final Utils utils = new Utils();

    /**
     * Cada opção do menu aponta para o jeito de criar a sua forma de pagamento
     * (ler os dados e montar o objeto). É uma consulta em um mapa, então não há
     * if/switch para identificar o tipo. Para adicionar uma nova forma de
     * pagamento, basta uma linha aqui (mais a classe e o item no menu).
     */
    private final Map<Integer, Supplier<FormaDePagamento>> formasDePagamento = Map.of(
        1, leitorDePagamento::lerCartaoDeCredito,
        2, leitorDePagamento::lerCartaoDeDebito,
        3, Pix::new // sem digitação: o sistema gera a chave
    );

    /**
     * Conduz o usuário pelo pagamento de uma compra.
     *
     * @param valorDaCompra valor total da compra, sem desconto (precisa ser maior que zero)
     * @return o comprovante se o pagamento foi aprovado; vazio se o usuário desistiu
     *         (opção "Voltar" ou não confirmou) ou se o pagamento foi recusado e ele não tentou de novo
     */
    public Optional<ComprovanteDePagamento> realizarPagamento (BigDecimal valorDaCompra) {
        if (valorDaCompra == null || valorDaCompra.signum() <= 0) {
            throw new IllegalArgumentException("O valor da compra deve ser maior que zero.");
        }

        while (true) {
            utils.limparTerminal();
            menuView.imprimirMenuPagamento();
            utils.imprimirMensagem("Valor da compra: " + utils.formatarMoeda(valorDaCompra) + "\n\n");

            int opcao = leitorDeOpcao.lerOpcao();
            utils.imprimirMensagem("\n");

            if (opcao == 0) {
                return Optional.empty();
            }

            Optional<FormaDePagamento> formaDePagamento = criarFormaDePagamento(opcao);

            if (formaDePagamento.isEmpty()) {
                utils.imprimirMensagem("O valor inserido não condiz com nenhuma das opções.\nInsira um valor entre 0 - 3.\n");
                utils.pausar();
                continue;
            }

            Optional<ComprovanteDePagamento> comprovante = pagarCom(formaDePagamento.get(), valorDaCompra);

            if (comprovante.isPresent()) {
                return comprovante;
            }
        }
    }

    /** Lê os dados da forma escolhida e a devolve; vazio se a opção não existir. */
    private Optional<FormaDePagamento> criarFormaDePagamento (int opcao) {
        return Optional.ofNullable(formasDePagamento.get(opcao)).map(Supplier::get);
    }

    /** Mostra o valor final, pede confirmação e processa. Vazio se não confirmou ou foi recusado. */
    private Optional<ComprovanteDePagamento> pagarCom (FormaDePagamento formaDePagamento, BigDecimal valorDaCompra) {
        BigDecimal valorFinal = formaDePagamento.calcularValorFinal(valorDaCompra); // polimorfismo

        utils.imprimirMensagem("\nForma de pagamento: " + formaDePagamento.getNome() + "\n");
        utils.imprimirMensagem("Valor da compra: " + utils.formatarMoeda(valorDaCompra) + "\n");
        utils.imprimirMensagem("Valor a pagar: " + utils.formatarMoeda(valorFinal) + "\n");

        // Polimorfismo: cada forma diz o que o usuário precisa ver (o Pix mostra a chave; os cartões, nada).
        formaDePagamento.getInstrucoesDePagamento().ifPresent(instrucoes -> utils.imprimirMensagem("\n" + instrucoes + "\n"));
        utils.imprimirMensagem("\n");

        if (!confirmarPagamento()) {
            utils.imprimirMensagem("\nPagamento cancelado.\n");
            utils.pausar();
            return Optional.empty();
        }

        try {
            ComprovanteDePagamento comprovante = formaDePagamento.pagar(valorDaCompra); // polimorfismo
            imprimirComprovante(comprovante);
            utils.pausar();
            return Optional.of(comprovante);
        } catch (PagamentoRecusadoException e) {
            utils.imprimirMensagem("\n" + e.getMessage() + "\n");
            utils.pausar();
            return Optional.empty();
        }
    }

    private void imprimirComprovante (ComprovanteDePagamento comprovante) {
        utils.imprimirMensagem("Pagamento aprovado!\n\n");
        utils.imprimirMensagem("Forma de pagamento: " + comprovante.formaDePagamento() + "\n");
        utils.imprimirMensagem("Valor da compra: " + utils.formatarMoeda(comprovante.valorDaCompra()) + "\n");
        utils.imprimirMensagem("Desconto: " + utils.formatarMoeda(comprovante.desconto()) + "\n");
        utils.imprimirMensagem("Valor pago: " + utils.formatarMoeda(comprovante.valorPago()) + "\n");
        utils.imprimirMensagem("Código de autorização: " + comprovante.codigoDeAutorizacao() + "\n");
    }

    /** Pergunta "1 - Sim / 2 - Não" até receber uma resposta válida. */
    private boolean confirmarPagamento () {
        while (true) {
            menuView.imprimirMenuConfirmarPagamento();
            int opcao = leitorDeOpcao.lerOpcao();

            if (opcao == 1) return true;
            if (opcao == 2) return false;

            utils.imprimirMensagem("Insira 1 para sim ou 2 para não.\n\n");
        }
    }
}
