package com.github.marcoadp.controle_investimentos.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.github.marcoadp.controle_investimentos.stub.FundoImobiliarioStub.*;
import static org.assertj.core.api.Assertions.assertThat;

class FundoImobiliarioMapperTest {

    private final FundoImobiliarioMapper fundoImobiliarioMapper = Mappers.getMapper(FundoImobiliarioMapper.class);

    @Test
    void toFundoImobiliario() {
        var fundoImobiliarioRequest = getFundoImobiliarioRequest();
        var fundoImobiliario = fundoImobiliarioMapper.toFundoImobiliario(fundoImobiliarioRequest);
        assertThat(fundoImobiliario.getId()).isNull();
        assertThat(fundoImobiliario.getNome()).isEqualTo(fundoImobiliarioRequest.nome());
        assertThat(fundoImobiliario.getCodigo()).isEqualTo(fundoImobiliarioRequest.codigo());
        assertThat(fundoImobiliario.getCnpj()).isEqualTo(fundoImobiliarioRequest.cnpj());
        assertThat(fundoImobiliario.getTipo()).isEqualTo(fundoImobiliarioRequest.tipo());
        assertThat(fundoImobiliario.getSegmento()).isEqualTo(fundoImobiliarioRequest.segmento());
    }

    @Test
    void toFundoImobiliarioResponse() {
        var fundoImobiliario = getFundoImobiliarioBuilder().id(1L).build();
        var fundoImobiliarioResponse = fundoImobiliarioMapper.toFundoImobiliarioResponse(fundoImobiliario);
        assertThat(fundoImobiliarioResponse.nome()).isEqualTo(fundoImobiliario.getNome());
        assertThat(fundoImobiliarioResponse.codigo()).isEqualTo(fundoImobiliario.getCodigo());
        assertThat(fundoImobiliarioResponse.cnpj()).isEqualTo(fundoImobiliario.getCnpj());
        assertThat(fundoImobiliarioResponse.tipo()).isEqualTo(fundoImobiliario.getTipo());
        assertThat(fundoImobiliarioResponse.segmento()).isEqualTo(fundoImobiliario.getSegmento());

    }

    @Test
    void updateFundoImobiliario() {
        var fundoImobiliario = getFundoImobiliarioBuilder().id(1L).build();
        var fundoImobiliarioRequest = getFundoImobiliarioRequest();
        fundoImobiliarioMapper.updateFundoImobiliario(fundoImobiliarioRequest, fundoImobiliario);
        assertThat(fundoImobiliario.getId()).isEqualTo(1L);
        assertThat(fundoImobiliario.getNome()).isEqualTo(fundoImobiliarioRequest.nome());
        assertThat(fundoImobiliario.getCodigo()).isEqualTo(fundoImobiliarioRequest.codigo());
        assertThat(fundoImobiliario.getCnpj()).isEqualTo(fundoImobiliarioRequest.cnpj());
        assertThat(fundoImobiliario.getTipo()).isEqualTo(fundoImobiliarioRequest.tipo());
        assertThat(fundoImobiliario.getSegmento()).isEqualTo(fundoImobiliarioRequest.segmento());
    }
}