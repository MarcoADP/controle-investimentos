package com.github.marcoadp.controle_investimentos.mapper;

import com.github.marcoadp.controle_investimentos.entity.Setor;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static com.github.marcoadp.controle_investimentos.stub.SetorStub.getSetor;
import static com.github.marcoadp.controle_investimentos.stub.SetorStub.getSetorRequest;
import static org.assertj.core.api.Assertions.assertThat;

class SetorMapperTest {

    private final SetorMapper setorMapper = Mappers.getMapper(SetorMapper.class);

    @Test
    void toSetor() {
        var setorRequest = getSetorRequest();
        var setor = setorMapper.toSetor(setorRequest);
        assertThat(setor.getNome()).isEqualTo(setorRequest.nome());
        assertThat(setor.getId()).isNull();
    }

    @Test
    void toSetorResponse() {
        var setor = getSetor();
        var setorResponse = setorMapper.toSetorResponse(setor);
        assertThat(setorResponse.nome()).isEqualTo(setor.getNome());

    }

    @Test
    void toSetorResponseList() {
        var setores = List.of(new Setor(1L, "Setor 1"), new Setor(2L, "Setor 2"));
        var setoresResponse = setorMapper.toSetorResponseList(setores);
        assertThat(setoresResponse).hasSize(2);
    }

    @Test
    void updateSetor() {
        var setor = getSetor();
        var setorRequest = getSetorRequest();
        setorMapper.updateSetor(setorRequest, setor);
        assertThat(setor.getNome()).isEqualTo(setorRequest.nome());
        assertThat(setor.getId()).isEqualTo(1L);
    }
}