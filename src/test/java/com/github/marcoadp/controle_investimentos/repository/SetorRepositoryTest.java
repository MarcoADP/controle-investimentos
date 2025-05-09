package com.github.marcoadp.controle_investimentos.repository;

import com.github.marcoadp.controle_investimentos.entity.Setor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SetorRepositoryTest {

    @Autowired
    SetorRepository setorRepository;

    @Test
    void criarTest() {
        var setor = criarSetor();
        assertThat(setor.getId()).isNotNull();
        assertThat(setor.getNome()).isEqualTo("Setor");
    }

    @Test
    void atualizarTest() {
        var setor = criarSetor();
        setor.setNome("Atualizado");
        setor = setorRepository.save(setor);
        assertThat(setor.getId()).isNotNull();
        assertThat(setor.getNome()).isEqualTo("Atualizado");
    }

    @Test
    void buscarPeloIdTest() {
        var setor = criarSetor();
        var setorOpt = setorRepository.findById(setor.getId());
        assertThat(setorOpt).isPresent();
        assertThat(setorOpt.get().getId()).isNotNull();
        assertThat(setorOpt.get().getNome()).isEqualTo("Setor");
    }

    @Test
    void removerTest() {
        var setor = criarSetor();
        setorRepository.deleteById(setor.getId());
        var setorOpt = setorRepository.findById(setor.getId());
        assertThat(setorOpt).isEmpty();
    }

    private Setor criarSetor() {
        var setor = new Setor();
        setor.setNome("Setor");
        setor = setorRepository.save(setor);
        return setor;
    }

}
