package com.github.marcoadp.controle_investimentos.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.github.marcoadp.controle_investimentos.stub.BdrStub.getBdr;
import static com.github.marcoadp.controle_investimentos.stub.BdrStub.getBdrRequest;
import static org.assertj.core.api.Assertions.assertThat;

class BdrMapperTest {

    private final BdrMapper bdrMapper = Mappers.getMapper(BdrMapper.class);

    @Test
    void toBdr() {
        var bdrRequest = getBdrRequest();
        var bdr = bdrMapper.toBdr(bdrRequest);
        assertThat(bdr.getId()).isNull();
        assertThat(bdr.getNome()).isEqualTo(bdrRequest.nome());
        assertThat(bdr.getCodigo()).isEqualTo(bdrRequest.codigo());
        assertThat(bdr.getCnpj()).isEqualTo(bdrRequest.cnpj());
        assertThat(bdr.getSetor()).isEqualTo(bdrRequest.setor());
    }

    @Test
    void toBdrResponse() {
        var bdr = getBdr(1L);
        var bdrResponse = bdrMapper.toBdrResponse(bdr);
        assertThat(bdrResponse.nome()).isEqualTo(bdr.getNome());
        assertThat(bdrResponse.codigo()).isEqualTo(bdr.getCodigo());
        assertThat(bdrResponse.cnpj()).isEqualTo(bdr.getCnpj());
        assertThat(bdrResponse.setor()).isEqualTo(bdr.getSetor());

    }

    @Test
    void updateBdr() {
        var bdr = getBdr(1L);
        var bdrRequest = getBdrRequest();
        bdrMapper.updateBdr(bdrRequest, bdr);
        assertThat(bdr.getId()).isEqualTo(1L);
        assertThat(bdr.getNome()).isEqualTo(bdrRequest.nome());
        assertThat(bdr.getCodigo()).isEqualTo(bdrRequest.codigo());
        assertThat(bdr.getCnpj()).isEqualTo(bdrRequest.cnpj());
        assertThat(bdr.getSetor()).isEqualTo(bdrRequest.setor());
    }
}