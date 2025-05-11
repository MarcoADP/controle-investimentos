package com.github.marcoadp.controle_investimentos.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.github.marcoadp.controle_investimentos.stub.ProventoStub.*;
import static org.assertj.core.api.Assertions.assertThat;

class ProventoMapperTest {

    private final ProventoMapper proventoMapper = Mappers.getMapper(ProventoMapper.class);

    @Test
    void toProvento() {
        var proventoRequest = getProventoRequest();
        var provento = proventoMapper.toProvento(proventoRequest);
        assertThat(provento.getId()).isNull();
        assertThat(provento).usingRecursiveComparison().isEqualTo(getProvento());
    }

    @Test
    void toProventoResponse() {
        var provento = getProvento();
        var proventoResponse = proventoMapper.toProventoResponse(provento);
        assertThat(proventoResponse).usingRecursiveComparison().isEqualTo(getProventoResponse());

    }
}