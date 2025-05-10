package com.github.marcoadp.controle_investimentos.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.github.marcoadp.controle_investimentos.stub.MovimentacaoStub.*;
import static org.assertj.core.api.Assertions.assertThat;

class MovimentacaoMapperTest {

    private final MovimentacaoMapper movimentacaoMapper = Mappers.getMapper(MovimentacaoMapper.class);

    @Test
    void toMovimentacao() {
        var movimentacaoRequest = getMovimentacaoRequest();
        var movimentacao = movimentacaoMapper.toMovimentacao(movimentacaoRequest);
        assertThat(movimentacao.getId()).isNull();
        assertThat(movimentacao).usingRecursiveComparison().isEqualTo(getMovimentacao());
    }

    @Test
    void toMovimentacaoResponse() {
        var movimentacao = getMovimentacao();
        var movimentacaoResponse = movimentacaoMapper.toMovimentacaoResponse(movimentacao);
        assertThat(movimentacaoResponse).usingRecursiveComparison().isEqualTo(getMovimentacaoResponse());

    }
}