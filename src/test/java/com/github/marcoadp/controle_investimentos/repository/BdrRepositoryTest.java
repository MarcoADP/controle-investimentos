package com.github.marcoadp.controle_investimentos.repository;

import com.github.marcoadp.controle_investimentos.entity.Bdr;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.github.marcoadp.controle_investimentos.stub.BdrStub.getBdr;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BdrRepositoryTest {

    @Autowired
    BdrRepository bdrRepository;

    @Test
    void save() {
        var bdr = criarBdr();
        assertBdr(bdr);
    }

    @Test
    void findById() {
        var bdr = criarBdr();
        var bdrOpt = bdrRepository.findById(bdr.getId());
        assertThat(bdrOpt).isPresent();
        assertBdr(bdrOpt.get());
    }

    @Test
    void findFirstByCodigo() {
        var bdr = criarBdr();
        var bdrOpt = bdrRepository.findFirstByCodigo(bdr.getCodigo());
        assertThat(bdrOpt).isPresent();
        assertBdr(bdrOpt.get());
    }

    @Test
    void findBySetor() {
        var bdr = criarBdr();
        var bdrs = bdrRepository.findBySetor(bdr.getSetor());
        assertThat(bdrs).hasSize(1);
        assertBdr(bdrs.getFirst());
    }

    @Test
    void removerTest() {
        var bdr = criarBdr();
        bdrRepository.deleteById(bdr.getId());
        var bdrOpt = bdrRepository.findById(bdr.getId());
        assertThat(bdrOpt).isEmpty();
    }

    private Bdr criarBdr() {
        var bdr = getBdr(null);
        return bdrRepository.save(bdr);
    }

    private static void assertBdr(Bdr bdr) {
        assertThat(bdr.getId()).isNotNull();
        assertThat(bdr.getNome()).isEqualTo("BDR");
        assertThat(bdr.getCodigo()).isEqualTo("BDRZ32");
        assertThat(bdr.getCnpj()).isEqualTo("26492737000162");
        assertThat(bdr.getSetor()).isEqualTo("Setor");
    }

}
