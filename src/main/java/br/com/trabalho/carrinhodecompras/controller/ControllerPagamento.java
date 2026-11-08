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

public class ControllerPagamento {
    private final LeitorDePagamento leitorDePagamento = new LeitorDePagamento();
    private final LeitorDeOpcao leitorDeOpcao = new LeitorDeOpcao();
    private final MenuView menuView = new MenuView();
    private final Utils utils = new Utils();

    private final Map<Integer, Supplier<FormaDePagamento>> formasDePagamento = Map.of(
        1, leitorDePagamento::lerCartaoDeCredito,
        2, leitorDePagamento::lerCartaoDeDebito,
        3, Pix::new 
    );

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

        formaDePagamento.getInstrucoesDePagamento().ifPresent(instrucoes -> utils.imprimirMensagem("\n" + instrucoes + "\n"));
        utils.imprimirMensagem("\n");

        if (!confirmarPagamento()) {
            utils.imprimirMensagem("\nPagamento cancelado.\n");
            utils.pausar();
            return Optional.empty();
        }

        try {
            ComprovanteDePagamento comprovante = formaDePagamento.pagar(valorDaCompra);
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
        utils.imprimirMensagem("\nPagamento aprovado!\n\n");
        utils.imprimirMensagem("Forma de pagamento: " + comprovante.formaDePagamento() + "\n");
        utils.imprimirMensagem("Valor da compra: " + utils.formatarMoeda(comprovante.valorDaCompra()) + "\n");
        utils.imprimirMensagem("Desconto: " + utils.formatarMoeda(comprovante.desconto()) + "\n");
        utils.imprimirMensagem("Valor pago: " + utils.formatarMoeda(comprovante.valorPago()) + "\n");
    }

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
