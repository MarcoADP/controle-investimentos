package com.github.marcoadp.controle_investimentos.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.github.marcoadp.controle_investimentos.stub.CotacaoHistoricoStub.getCotacaoHistorico;
import static com.github.marcoadp.controle_investimentos.stub.CotacaoHistoricoStub.getCotacaoHistoricoResponse;
import static org.assertj.core.api.Assertions.assertThat;

class CotacaoHistoricoMapperTest {

    private final CotacaoHistoricoMapper cotacaoHistoricoMapper = Mappers.getMapper(CotacaoHistoricoMapper.class);

    @Test
    void toCotacaoHistoricoResponse() {
        var cotacao = getCotacaoHistorico();
        var cotacaoResponse = cotacaoHistoricoMapper.toCotacaoHistoricoResponse(cotacao);
        assertThat(cotacaoResponse).usingRecursiveComparison().isEqualTo(getCotacaoHistoricoResponse());

    }
}