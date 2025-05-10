package com.github.marcoadp.controle_investimentos.repository;

import com.github.marcoadp.controle_investimentos.entity.Movimentacao;
import com.github.marcoadp.controle_investimentos.enums.OperacaoEnum;
import com.github.marcoadp.controle_investimentos.enums.TipoAtivoEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MovimentacaoRepositoryTest {

    @Autowired
    MovimentacaoRepository movimentacaoRepository;

    @Test
    void save() {
        var movimentacao = criarMovimentacao();
        assertMovimentacao(movimentacao);
    }

    @Test
    void findById() {
        var movimentacao = criarMovimentacao();
        var movimentacaoOpt = movimentacaoRepository.findById(movimentacao.getId());
        assertThat(movimentacaoOpt).isPresent();
        assertMovimentacao(movimentacaoOpt.get());
    }

    @Test
    void findByCodigo() {
        var movimentacao = criarMovimentacao();
        var movimentacoes = movimentacaoRepository.findByCodigo(movimentacao.getCodigo());
        assertThat(movimentacoes).hasSize(1);
        assertMovimentacao(movimentacoes.getFirst());
    }

    @Test
    void deleteById() {
        var movimentacao = criarMovimentacao();
        movimentacaoRepository.deleteById(movimentacao.getId());
        var movimentacaoOpt = movimentacaoRepository.findById(movimentacao.getId());
        assertThat(movimentacaoOpt).isEmpty();
    }

    private Movimentacao criarMovimentacao() {
        var movimentacao = Movimentacao.builder()
                .data(LocalDate.of(2025,1, 1))
                .operacao(OperacaoEnum.ENTRADA)
                .codigo("ACAO4")
                .tipoAtivo(TipoAtivoEnum.ACAO)
                .quantidade(BigDecimal.TEN)
                .valorUnitario(BigDecimal.ONE)
                .valorTotal(BigDecimal.TEN)
                .build();
        movimentacao = movimentacaoRepository.save(movimentacao);
        return movimentacao;
    }

    private static void assertMovimentacao(Movimentacao movimentacao) {
        assertThat(movimentacao.getId()).isNotNull();
        assertThat(movimentacao.getData()).isEqualTo(LocalDate.parse("2025-01-01"));
        assertThat(movimentacao.getOperacao()).isEqualTo(OperacaoEnum.ENTRADA);
        assertThat(movimentacao.getCodigo()).isEqualTo("ACAO4");
        assertThat(movimentacao.getTipoAtivo()).isEqualTo(TipoAtivoEnum.ACAO);
        assertThat(movimentacao.getQuantidade()).isEqualTo(BigDecimal.TEN);
        assertThat(movimentacao.getValorUnitario()).isEqualTo(BigDecimal.ONE);
        assertThat(movimentacao.getValorTotal()).isEqualTo(BigDecimal.TEN);
    }

}
