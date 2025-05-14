package com.github.marcoadp.controle_investimentos.repository;

import com.github.marcoadp.controle_investimentos.entity.Etf;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.github.marcoadp.controle_investimentos.stub.EtfStub.getEtf;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EtfRepositoryTest {

    @Autowired
    EtfRepository etfRepository;

    @Test
    void save() {
        var etf = criarEtf();
        assertEtf(etf);
    }

    @Test
    void findById() {
        var etf = criarEtf();
        var etfOpt = etfRepository.findById(etf.getId());
        assertThat(etfOpt).isPresent();
        assertEtf(etfOpt.get());
    }

    @Test
    void findFirstByCodigo() {
        var etf = criarEtf();
        var etfOpt = etfRepository.findFirstByCodigo(etf.getCodigo());
        assertThat(etfOpt).isPresent();
        assertEtf(etfOpt.get());
    }

    @Test
    void findBySetor() {
        var etf = criarEtf();
        var etfs = etfRepository.findByTipo(etf.getTipo());
        assertThat(etfs).hasSize(1);
        assertEtf(etfs.getFirst());
    }

    @Test
    void removerTest() {
        var etf = criarEtf();
        etfRepository.deleteById(etf.getId());
        var etfOpt = etfRepository.findById(etf.getId());
        assertThat(etfOpt).isEmpty();
    }

    private Etf criarEtf() {
        var etf = getEtf(null);
        return etfRepository.save(etf);
    }

    private static void assertEtf(Etf etf) {
        assertThat(etf.getId()).isNotNull();
        assertThat(etf.getNome()).isEqualTo("ETF");
        assertThat(etf.getCodigo()).isEqualTo("ETF11");
        assertThat(etf.getCnpj()).isEqualTo("26492737000162");
        assertThat(etf.getTipo()).isEqualTo("Tipo");
    }

}
