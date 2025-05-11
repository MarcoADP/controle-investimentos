package com.github.marcoadp.controle_investimentos.repository;

import com.github.marcoadp.controle_investimentos.entity.Provento;
import com.github.marcoadp.controle_investimentos.enums.TipoProventoEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static com.github.marcoadp.controle_investimentos.stub.ProventoStub.getProvento;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProventoRepositoryTest {

    @Autowired
    ProventoRepository proventoRepository;

    @Test
    void save() {
        var provento = criarProvento();
        assertProvento(provento);
    }

    @Test
    void findById() {
        var provento = criarProvento();
        var proventoOpt = proventoRepository.findById(provento.getId());
        assertThat(proventoOpt).isPresent();
        assertProvento(proventoOpt.get());
    }

    @Test
    void findByCodigo() {
        var provento = criarProvento();
        var proventos = proventoRepository.findByCodigo(provento.getCodigo());
        assertThat(proventos).hasSize(1);
        assertProvento(proventos.getFirst());
    }

    @Test
    void findByTipoProvento() {
        var provento = criarProvento();
        var proventos = proventoRepository.findByTipoProvento(provento.getTipoProvento());
        assertThat(proventos).hasSize(1);
        assertProvento(proventos.getFirst());
    }

    @Test
    void deleteById() {
        var provento = criarProvento();
        proventoRepository.deleteById(provento.getId());
        var proventoOpt = proventoRepository.findById(provento.getId());
        assertThat(proventoOpt).isEmpty();
    }

    private Provento criarProvento() {
        var provento = getProvento();
        provento = proventoRepository.save(provento);
        return provento;
    }

    private static void assertProvento(Provento provento) {
        assertThat(provento.getId()).isNotNull();
        assertThat(provento.getDataPagamento()).isEqualTo(LocalDate.parse("2025-01-01"));
        assertThat(provento.getTipoProvento()).isEqualTo(TipoProventoEnum.DIVIDENDOS);
        assertThat(provento.getCodigo()).isEqualTo("ACAO4");
        assertThat(provento.getQuantidade()).isEqualTo(10);
        assertThat(provento.getValorTotal()).isEqualTo(BigDecimal.TEN);
        assertThat(provento.getValorMedio()).isEqualTo(BigDecimal.ONE.setScale(5, RoundingMode.HALF_UP));
    }

}
