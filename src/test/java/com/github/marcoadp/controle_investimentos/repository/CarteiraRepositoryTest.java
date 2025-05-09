package com.github.marcoadp.controle_investimentos.repository;

import com.github.marcoadp.controle_investimentos.entity.Carteira;
import com.github.marcoadp.controle_investimentos.entity.CarteiraAtivo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CarteiraRepositoryTest {

    @Autowired
    CarteiraRepository carteiraRepository;

    @Test
    void save() {
        var carteira = criarCarteira();
        assertCarteira(carteira);
    }

    @Test
    void buscarPeloIdTest() {
        var carteira = criarCarteira();
        var carteiraOpt = carteiraRepository.findById(carteira.getId());
        assertThat(carteiraOpt).isPresent();
        assertCarteira(carteiraOpt.get());
    }

    @Test
    void removerTest() {
        var carteira = criarCarteira();
        carteiraRepository.deleteById(carteira.getId());
        var carteiraOpt = carteiraRepository.findById(carteira.getId());
        assertThat(carteiraOpt).isEmpty();
    }

    private Carteira criarCarteira() {
        var ativo1 = CarteiraAtivo.builder().codigo("ACAO4").build();
        var ativo2 = CarteiraAtivo.builder().codigo("ITBA3").build();
        var ativos = List.of(ativo1, ativo2);
        var carteira = Carteira.builder()
                .nome("Carteira")
                .descricao("Carteira de ativos")
                .ativos(ativos)
                .build();
        carteira = carteiraRepository.save(carteira);
        return carteira;
    }

    private static void assertCarteira(Carteira carteira) {
        assertThat(carteira.getId()).isNotNull();
        assertThat(carteira.getNome()).isEqualTo("Carteira");
        assertThat(carteira.getDescricao()).isEqualTo("Carteira de ativos");
        assertThat(carteira.getAtivos()).hasSize(2);
        var ativo1 = carteira.getAtivos().getFirst();
        assertThat(ativo1.getCodigo()).isEqualTo("ACAO4");
        var ativo2 = carteira.getAtivos().get(1);
        assertThat(ativo2.getCodigo()).isEqualTo("ITBA3");
    }

}
