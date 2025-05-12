package com.github.marcoadp.controle_investimentos.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.github.marcoadp.controle_investimentos.stub.ConsolidacaoProventoStub.getConsolidacaoProvento;
import static com.github.marcoadp.controle_investimentos.stub.ConsolidacaoProventoStub.getConsolidacaoProventoResponse;
import static org.assertj.core.api.Assertions.assertThat;

class ConsolidacaoProventoMapperTest {

    private final ConsolidacaoProventoMapper consolidacaoMapper = Mappers.getMapper(ConsolidacaoProventoMapper.class);

    @Test
    void toConsolidacaoProventoResponse() {
        var consolidacao = getConsolidacaoProvento();
        var consolidacaoResponse = consolidacaoMapper.toConsolidacaoProventoResponse(consolidacao);
        assertThat(consolidacaoResponse).usingRecursiveComparison().isEqualTo(getConsolidacaoProventoResponse());

    }
}