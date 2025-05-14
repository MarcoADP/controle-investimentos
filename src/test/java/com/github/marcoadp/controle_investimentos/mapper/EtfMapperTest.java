package com.github.marcoadp.controle_investimentos.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.github.marcoadp.controle_investimentos.stub.EtfStub.getEtf;
import static com.github.marcoadp.controle_investimentos.stub.EtfStub.getEtfRequest;
import static org.assertj.core.api.Assertions.assertThat;

class EtfMapperTest {

    private final EtfMapper etfMapper = Mappers.getMapper(EtfMapper.class);

    @Test
    void toEtf() {
        var etfRequest = getEtfRequest();
        var etf = etfMapper.toEtf(etfRequest);
        assertThat(etf.getId()).isNull();
        assertThat(etf.getNome()).isEqualTo(etfRequest.nome());
        assertThat(etf.getCodigo()).isEqualTo(etfRequest.codigo());
        assertThat(etf.getCnpj()).isEqualTo(etfRequest.cnpj());
        assertThat(etf.getTipo()).isEqualTo(etfRequest.tipo());
    }

    @Test
    void toEtfResponse() {
        var etf = getEtf(1L);
        var etfResponse = etfMapper.toEtfResponse(etf);
        assertThat(etfResponse.nome()).isEqualTo(etf.getNome());
        assertThat(etfResponse.codigo()).isEqualTo(etf.getCodigo());
        assertThat(etfResponse.cnpj()).isEqualTo(etf.getCnpj());
        assertThat(etfResponse.tipo()).isEqualTo(etf.getTipo());

    }

    @Test
    void updateEtf() {
        var etf = getEtf(1L);
        var etfRequest = getEtfRequest();
        etfMapper.updateEtf(etfRequest, etf);
        assertThat(etf.getId()).isEqualTo(1L);
        assertThat(etf.getNome()).isEqualTo(etfRequest.nome());
        assertThat(etf.getCodigo()).isEqualTo(etfRequest.codigo());
        assertThat(etf.getCnpj()).isEqualTo(etfRequest.cnpj());
        assertThat(etf.getTipo()).isEqualTo(etfRequest.tipo());
    }
}