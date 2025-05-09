package com.github.marcoadp.controle_investimentos.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.github.marcoadp.controle_investimentos.stub.CarteiraStub.getCarteira;
import static com.github.marcoadp.controle_investimentos.stub.CarteiraStub.getCarteiraRequest;
import static org.assertj.core.api.Assertions.assertThat;

class CarteiraMapperTest {

    private final CarteiraMapper carteiraMapper = Mappers.getMapper(CarteiraMapper.class);

    @Test
    void toCarteira() {
        var carteiraRequest = getCarteiraRequest();
        var carteira = carteiraMapper.toCarteira(carteiraRequest);
        assertThat(carteira.getId()).isNull();
        assertThat(carteira.getNome()).isEqualTo("Carteira");
        assertThat(carteira.getDescricao()).isEqualTo("Carteira de Ativos");
        assertThat(carteira.getAtivos()).hasSize(1);
        assertThat(carteira.getAtivos().getFirst().getCodigo()).isEqualTo("ACAO4");
    }

    @Test
    void toCarteiraResponse() {
        var carteira = getCarteira();
        var carteiraResponse = carteiraMapper.toCarteiraResponse(carteira);
        assertThat(carteiraResponse.nome()).isEqualTo(carteira.getNome());
        assertThat(carteiraResponse.descricao()).isEqualTo("Carteira de Ativos");
        assertThat(carteiraResponse.ativos()).hasSize(1);
        assertThat(carteiraResponse.ativos().getFirst().codigo()).isEqualTo("ACAO4");

    }
}