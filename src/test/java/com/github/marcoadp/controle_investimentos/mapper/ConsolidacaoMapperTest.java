package com.github.marcoadp.controle_investimentos.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.github.marcoadp.controle_investimentos.stub.ConsolidacaoStub.*;
import static org.assertj.core.api.Assertions.assertThat;

class ConsolidacaoMapperTest {

    private final ConsolidacaoMapper consolidacaoMapper = Mappers.getMapper(ConsolidacaoMapper.class);

    @Test
    void toConsolidacaoResponse() {
        var consolidacao = getConsolidacao();
        var consolidacaoResponse = consolidacaoMapper.toConsolidacaoResponse(consolidacao);
        assertThat(consolidacaoResponse).usingRecursiveComparison().isEqualTo(getConsolidacaoResponse());

    }
}