package com.github.marcoadp.controle_investimentos.repository;

import com.github.marcoadp.controle_investimentos.entity.Acao;
import com.github.marcoadp.controle_investimentos.entity.Setor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AcaoRepositoryTest {

    @Autowired
    AcaoRepository acaoRepository;

    @Test
    void save() {
        var acao = criarAcao();
        assertAcao(acao);
    }

    @Test
    void findById() {
        var acao = criarAcao();
        var acaoOpt = acaoRepository.findById(acao.getId());
        assertThat(acaoOpt).isPresent();
        assertAcao(acaoOpt.get());
    }

    @Test
    void findFirstByCodigo() {
        var acao = criarAcao();
        var acaoOpt = acaoRepository.findFirstByCodigo(acao.getCodigo());
        assertThat(acaoOpt).isPresent();
        assertAcao(acaoOpt.get());
    }

    @Test
    void findBySetor() {
        var acao = criarAcao();
        var acoes = acaoRepository.findBySetor(acao.getSetor().getId());
        assertThat(acoes).hasSize(1);
        assertAcao(acoes.getFirst());
    }

    @Test
    void removerTest() {
        var acao = criarAcao();
        acaoRepository.deleteById(acao.getId());
        var acaoOpt = acaoRepository.findById(acao.getId());
        assertThat(acaoOpt).isEmpty();
    }

    private Acao criarAcao() {
        var setor = new Setor("Setor");
        var acao = Acao.builder()
                .nome("Acao")
                .codigo("ACAO4")
                .cnpj("26492737000162")
                .setor(setor)
                .build();
        return acaoRepository.save(acao);
    }

    private static void assertAcao(Acao acao) {
        assertThat(acao.getId()).isNotNull();
        assertThat(acao.getNome()).isEqualTo("Acao");
        assertThat(acao.getCodigo()).isEqualTo("ACAO4");
        assertThat(acao.getCnpj()).isEqualTo("26492737000162");
        assertThat(acao.getSetor().getNome()).isEqualTo("Setor");
    }

}
