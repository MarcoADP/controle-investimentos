package com.github.marcoadp.controle_investimentos.repository;

import com.github.marcoadp.controle_investimentos.entity.CotacaoHistorico;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.github.marcoadp.controle_investimentos.stub.CotacaoHistoricoStub.getCotacaoHistorico;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CotacaoHistoricoRepositoryTest {

    @Autowired
    CotacaoHistoricoRepository cotacaoRepository;

    @Test
    void save() {
        var cotacao = criarCotacaoHistorico();
        assertCotacaoHistorico(cotacao);
    }

    @Test
    void findById() {
        var cotacao = criarCotacaoHistorico();
        var cotacaoOpt = cotacaoRepository.findById(cotacao.getId());
        assertThat(cotacaoOpt).isPresent();
        assertCotacaoHistorico(cotacaoOpt.get());
    }

    @Test
    void findByCodigo() {
        var cotacao = criarCotacaoHistorico();
        var cotacoes = cotacaoRepository.findByCodigo(cotacao.getCodigo());
        assertThat(cotacoes).hasSize(1);
        assertCotacaoHistorico(cotacoes.getFirst());
    }

    @Test
    void findByData() {
        criarCotacaoHistorico();
        var cotacoes = cotacaoRepository.findByData(LocalDate.parse("2024-01-01"));
        assertThat(cotacoes).hasSize(1);
        assertCotacaoHistorico(cotacoes.getFirst());
    }

    @Test
    void findByCodigoAndData() {
        var cotacao = criarCotacaoHistorico();
        var cotacaoOpt = cotacaoRepository.findByCodigoAndData(cotacao.getCodigo(), LocalDate.parse("2024-01-01"));
        assertThat(cotacaoOpt).isPresent();
        assertCotacaoHistorico(cotacaoOpt.get());
    }

    @Test
    void deleteById() {
        var cotacao = criarCotacaoHistorico();
        cotacaoRepository.deleteById(cotacao.getId());
        var cotacaoOpt = cotacaoRepository.findById(cotacao.getId());
        assertThat(cotacaoOpt).isEmpty();
    }

    private CotacaoHistorico criarCotacaoHistorico() {
        var cotacao = getCotacaoHistorico();
        cotacao = cotacaoRepository.save(cotacao);
        return cotacao;
    }

    private static void assertCotacaoHistorico(CotacaoHistorico cotacao) {
        assertThat(cotacao.getId()).isNotNull();
        assertThat(cotacao.getCodigo()).isEqualTo("ACAO4");
        assertThat(cotacao.getValor()).isEqualTo(BigDecimal.TEN);
        assertThat(cotacao.getData()).isEqualTo(LocalDate.parse("2024-01-01"));
        assertThat(cotacao.getQuantidade()).isEqualTo(10);
        assertThat(cotacao.getValorTotal()).isEqualTo(BigDecimal.valueOf(100L));
        assertThat(cotacao.getValorCompra()).isEqualTo(BigDecimal.valueOf(90L));
        assertThat(cotacao.getVariacao()).isEqualTo(BigDecimal.valueOf(0.01));
    }

}
