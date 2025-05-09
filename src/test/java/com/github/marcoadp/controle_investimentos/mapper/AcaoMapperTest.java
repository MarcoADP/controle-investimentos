package com.github.marcoadp.controle_investimentos.mapper;

import com.github.marcoadp.controle_investimentos.dto.request.AcaoRequest;
import com.github.marcoadp.controle_investimentos.dto.response.AcaoResponse;
import com.github.marcoadp.controle_investimentos.entity.Acao;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static com.github.marcoadp.controle_investimentos.stub.AcaoStub.getAcao;
import static com.github.marcoadp.controle_investimentos.stub.AcaoStub.getAcaoRequest;
import static org.assertj.core.api.Assertions.assertThat;

class AcaoMapperTest {

    private final AcaoMapper acaoMapper = Mappers.getMapper(AcaoMapper.class);

    @Test
    void toAcao() {
        var acaoRequest = getAcaoRequest();
        var acao = acaoMapper.toAcao(acaoRequest);
        assertAcao(acao, acaoRequest);
        assertThat(acao.getId()).isNull();
        assertThat(acao.getSetor()).isNull();
    }

    @Test
    void toAcaoResponse() {
        var acao = getAcao();
        var acaoResponse = acaoMapper.toAcaoResponse(acao);
        assertResponse(acaoResponse, acao);

    }

    @Test
    void toAcaoResponseList() {
        var acoes = List.of(getAcao());
        var acoesResponse = acaoMapper.toAcaoResponseList(acoes);
        assertThat(acoesResponse).hasSize(1);
        assertResponse(acoesResponse.getFirst(), acoes.getFirst());
    }

    private static void assertResponse(AcaoResponse acaoResponse, Acao acao) {
        assertThat(acaoResponse.nome()).isEqualTo(acao.getNome());
        assertThat(acaoResponse.codigo()).isEqualTo(acao.getCodigo());
        assertThat(acaoResponse.cnpj()).isEqualTo(acao.getCnpj());
        assertThat(acaoResponse.setor()).isEqualTo(acao.getSetor().getNome());
    }

    @Test
    void updateAcao() {
        var acao = getAcao();
        var acaoRequest = getAcaoRequest();
        acaoMapper.updateAcao(acaoRequest, acao);
        assertAcao(acao, acaoRequest);
        assertThat(acao.getId()).isEqualTo(1L);
    }

    private static void assertAcao(Acao acao, AcaoRequest acaoRequest) {
        assertThat(acao.getNome()).isEqualTo(acaoRequest.nome());
        assertThat(acao.getCodigo()).isEqualTo(acaoRequest.codigo());
        assertThat(acao.getCnpj()).isEqualTo(acaoRequest.cnpj());
    }
}