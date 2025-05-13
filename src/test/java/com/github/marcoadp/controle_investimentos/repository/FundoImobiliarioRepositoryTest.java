package com.github.marcoadp.controle_investimentos.repository;

import com.github.marcoadp.controle_investimentos.entity.FundoImobiliario;
import com.github.marcoadp.controle_investimentos.repository.spec.FundoImobiliarioSpec;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static com.github.marcoadp.controle_investimentos.stub.FundoImobiliarioStub.getFundoImobiliarioBuilder;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FundoImobiliarioRepositoryTest {

    @Autowired
    FundoImobiliarioRepository fundoImobiliarioRepository;

    @Test
    void save() {
        var fundoImobiliario = criarFundoImobiliario();
        assertFundoImobiliario(fundoImobiliario);
    }

    @Test
    void findById() {
        var fundoImobiliario = criarFundoImobiliario();
        var fundoImobiliarioOpt = fundoImobiliarioRepository.findById(fundoImobiliario.getId());
        assertThat(fundoImobiliarioOpt).isPresent();
        assertFundoImobiliario(fundoImobiliarioOpt.get());
    }

    @Test
    void findFirstByCodigo() {
        var fundoImobiliario = criarFundoImobiliario();
        var fundoImobiliarioOpt = fundoImobiliarioRepository.findFirstByCodigo(fundoImobiliario.getCodigo());
        assertThat(fundoImobiliarioOpt).isPresent();
        assertFundoImobiliario(fundoImobiliarioOpt.get());
    }

    @Test
    void remover() {
        var fundoImobiliario = criarFundoImobiliario();
        fundoImobiliarioRepository.deleteById(fundoImobiliario.getId());
        var fundoImobiliarioOpt = fundoImobiliarioRepository.findById(fundoImobiliario.getId());
        assertThat(fundoImobiliarioOpt).isEmpty();
    }

    @Test
    void fundoImobiliarioSpecSemClausula() {
        criarFundos();
        var fundoSpec = FundoImobiliarioSpec.where(null, null, null);
        var fundos = fundoImobiliarioRepository.findAll();
        assertThat(fundos).hasSize(5);
    }

    @Test
    void fundoImobiliarioSpecComCodigo() {
        criarFundos();
        var fundoSpec = FundoImobiliarioSpec.where("TP1SE1", null, null);
        var fundos = fundoImobiliarioRepository.findAll(fundoSpec);
        assertThat(fundos).hasSize(1);
    }

    @Test
    void fundoImobiliarioSpecComTipo() {
        criarFundos();
        var fundoSpec = FundoImobiliarioSpec.where(null, "Tipo 1", null);
        var fundos = fundoImobiliarioRepository.findAll(fundoSpec);
        assertThat(fundos).hasSize(2);
    }

    @Test
    void fundoImobiliarioSpecComSegmento() {
        criarFundos();
        var fundoSpec = FundoImobiliarioSpec.where(null, null, "Segmento 1");
        var fundos = fundoImobiliarioRepository.findAll(fundoSpec);
        assertThat(fundos).hasSize(3);
    }

    @Test
    void fundoImobiliarioSpecComTipoESegmento() {
        criarFundos();
        var fundoSpec = FundoImobiliarioSpec.where(null, "Tipo 2", "Segmento 1");
        var fundos = fundoImobiliarioRepository.findAll(fundoSpec);
        assertThat(fundos).hasSize(1);
    }

    private FundoImobiliario criarFundoImobiliario() {
        var fundoImobiliario = getFundoImobiliarioBuilder().build();
        return fundoImobiliarioRepository.save(fundoImobiliario);
    }

    private List<FundoImobiliario> criarFundos() {
        List<FundoImobiliario> fundos = new ArrayList<>();
        fundos.add(getFundoImobiliarioBuilder().codigo("TP1SE1").tipo("Tipo 1").segmento("Segmento 1").build());
        fundos.add(getFundoImobiliarioBuilder().codigo("TP1SE2").tipo("Tipo 1").segmento("Segmento 2").build());
        fundos.add(getFundoImobiliarioBuilder().codigo("TP2SE1").tipo("Tipo 2").segmento("Segmento 1").build());
        fundos.add(getFundoImobiliarioBuilder().codigo("TP2SE2").tipo("Tipo 2").segmento("Segmento 2").build());
        fundos.add(getFundoImobiliarioBuilder().codigo("TP3SE1").tipo("Tipo 3").segmento("Segmento 1").build());
        return fundoImobiliarioRepository.saveAll(fundos);
    }

    private static void assertFundoImobiliario(FundoImobiliario fundoImobiliario) {
        assertThat(fundoImobiliario.getId()).isNotNull();
        assertThat(fundoImobiliario.getNome()).isEqualTo("FI");
        assertThat(fundoImobiliario.getCodigo()).isEqualTo("FIMO11");
        assertThat(fundoImobiliario.getCnpj()).isEqualTo("26492737000162");
        assertThat(fundoImobiliario.getTipo()).isEqualTo("Tipo");
        assertThat(fundoImobiliario.getSegmento()).isEqualTo("Segmento");
    }

}
