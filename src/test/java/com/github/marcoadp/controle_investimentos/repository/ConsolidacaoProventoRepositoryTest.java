package com.github.marcoadp.controle_investimentos.repository;

import com.github.marcoadp.controle_investimentos.entity.ConsolidacaoProvento;
import com.github.marcoadp.controle_investimentos.enums.TipoProventoEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static com.github.marcoadp.controle_investimentos.stub.ConsolidacaoProventoStub.getConsolidacaoProvento;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ConsolidacaoProventoRepositoryTest {

    @Autowired
    ConsolidacaoProventoRepository consolidacaoRepository;

    @Test
    void save() {
        var consolidacao = criarConsolidacaoProvento();
        assertConsolidacaoProvento(consolidacao);
    }

    @Test
    void findById() {
        var consolidacao = criarConsolidacaoProvento();
        var consolidacaoOpt = consolidacaoRepository.findById(consolidacao.getId());
        assertThat(consolidacaoOpt).isPresent();
        assertConsolidacaoProvento(consolidacaoOpt.get());
    }

    @Test
    void findByCodigo() {
        var consolidacao = criarConsolidacaoProvento();
        var consolidacoes = consolidacaoRepository.findByCodigo(consolidacao.getCodigo());
        assertThat(consolidacoes).hasSize(1);
        assertConsolidacaoProvento(consolidacoes.getFirst());
    }

    @Test
    void findByCodigoAndAnoAndTipoProvento() {
        var consolidacao = criarConsolidacaoProvento();
        var consolidacaoOpt = consolidacaoRepository.findFirstByCodigoAndAnoAndTipoProvento(consolidacao.getCodigo(), 2025, TipoProventoEnum.DIVIDENDOS);
        assertThat(consolidacaoOpt).isPresent();
        assertConsolidacaoProvento(consolidacaoOpt.get());
    }

    @Test
    void deleteById() {
        var consolidacao = criarConsolidacaoProvento();
        consolidacaoRepository.deleteById(consolidacao.getId());
        var consolidacaoOpt = consolidacaoRepository.findById(consolidacao.getId());
        assertThat(consolidacaoOpt).isEmpty();
    }

    private ConsolidacaoProvento criarConsolidacaoProvento() {
        var consolidacao = getConsolidacaoProvento();
        consolidacao = consolidacaoRepository.save(consolidacao);
        return consolidacao;
    }

    private static void assertConsolidacaoProvento(ConsolidacaoProvento consolidacao) {
        assertThat(consolidacao.getId()).isNotNull();
        assertThat(consolidacao.getCodigo()).isEqualTo("ACAO4");
        assertThat(consolidacao.getAno()).isEqualTo(2025);
        assertThat(consolidacao.getTipoProvento()).isEqualTo(TipoProventoEnum.DIVIDENDOS);
        assertThat(consolidacao.getValorTotal()).isEqualTo(BigDecimal.TEN);
        assertThat(consolidacao.getValorMedio()).isEqualTo(BigDecimal.ONE);
    }

}
