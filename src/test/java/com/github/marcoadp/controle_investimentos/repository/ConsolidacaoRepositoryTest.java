package com.github.marcoadp.controle_investimentos.repository;

import com.github.marcoadp.controle_investimentos.entity.Consolidacao;
import com.github.marcoadp.controle_investimentos.enums.TipoAtivoEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.github.marcoadp.controle_investimentos.stub.ConsolidacaoStub.getConsolidacao;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ConsolidacaoRepositoryTest {

    @Autowired
    ConsolidacaoRepository consolidacaoRepository;

    @Test
    void save() {
        var consolidacao = criarConsolidacao();
        assertConsolidacao(consolidacao);
    }

    @Test
    void findById() {
        var consolidacao = criarConsolidacao();
        var consolidacaoOpt = consolidacaoRepository.findById(consolidacao.getId());
        assertThat(consolidacaoOpt).isPresent();
        assertConsolidacao(consolidacaoOpt.get());
    }

    @Test
    void findByCodigo() {
        var consolidacao = criarConsolidacao();
        var consolidacaoOpt = consolidacaoRepository.findFirstByCodigo(consolidacao.getCodigo());
        assertThat(consolidacaoOpt).isPresent();
        assertConsolidacao(consolidacaoOpt.get());
    }

    @Test
    void deleteById() {
        var consolidacao = criarConsolidacao();
        consolidacaoRepository.deleteById(consolidacao.getId());
        var consolidacaoOpt = consolidacaoRepository.findById(consolidacao.getId());
        assertThat(consolidacaoOpt).isEmpty();
    }

    private Consolidacao criarConsolidacao() {
        var consolidacao = getConsolidacao();
        consolidacao = consolidacaoRepository.save(consolidacao);
        return consolidacao;
    }

    private static void assertConsolidacao(Consolidacao consolidacao) {
        assertThat(consolidacao.getId()).isNotNull();
        assertThat(consolidacao.getCodigo()).isEqualTo("ACAO4");
        assertThat(consolidacao.getTipoAtivo()).isEqualTo(TipoAtivoEnum.ACAO);
        assertThat(consolidacao.getQuantidadeEntrada()).isEqualTo(BigDecimal.TEN);
        assertThat(consolidacao.getValorMedioEntrada()).isEqualTo(BigDecimal.ONE.setScale(2, RoundingMode.HALF_UP));
        assertThat(consolidacao.getValorTotalEntrada()).isEqualTo(BigDecimal.TEN);
        assertThat(consolidacao.getQuantidadeSaida()).isEqualTo(BigDecimal.ZERO);
        assertThat(consolidacao.getValorMedioSaida()).isEqualTo(BigDecimal.ZERO);
        assertThat(consolidacao.getValorTotalSaida()).isEqualTo(BigDecimal.ZERO);
        assertThat(consolidacao.getLucroMedio()).isEqualTo(BigDecimal.ZERO);
        assertThat(consolidacao.getLucroProporcao()).isEqualTo(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP));
    }

}
