package com.github.marcoadp.controle_investimentos.mapper;

import com.github.marcoadp.controle_investimentos.dto.request.SetorRequest;
import com.github.marcoadp.controle_investimentos.entity.Setor;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SetorMapperTest {

    private SetorMapper setorMapper = Mappers.getMapper(SetorMapper.class);

    @Test
    void toSetor() {
        var setorRequest = new SetorRequest("Setor");
        var setor = setorMapper.toSetor(setorRequest);
        assertThat(setor.getNome()).isEqualTo(setorRequest.nome());
        assertThat(setor.getId()).isNull();
    }

    @Test
    void toSetorResponse() {
        var setor = new Setor(1L, "Setor");
        var setorResponse = setorMapper.toSetorResponse(setor);
        assertThat(setorResponse.nome()).isEqualTo(setor.getNome());

    }

    @Test
    void toSetorResponseList() {
        var setores = List.of(new Setor(1L, "Setor"), new Setor(2L, "Setor 2"));
        var setoresResponse = setorMapper.toSetorResponseList(setores);
        assertThat(setoresResponse).hasSize(2);
    }

    @Test
    void updateSetor() {
        var setor = new Setor(1L, "Setor");
        var setorRequest = new SetorRequest("Setor Atualizado");
        setorMapper.updateSetor(setorRequest, setor);
        assertThat(setor.getNome()).isEqualTo(setorRequest.nome());
        assertThat(setor.getId()).isEqualTo(1L);
    }
}