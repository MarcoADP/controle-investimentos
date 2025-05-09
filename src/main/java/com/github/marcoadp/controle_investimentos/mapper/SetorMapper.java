package com.github.marcoadp.controle_investimentos.mapper;

import com.github.marcoadp.controle_investimentos.dto.request.SetorRequest;
import com.github.marcoadp.controle_investimentos.dto.response.SetorResponse;
import com.github.marcoadp.controle_investimentos.entity.Setor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SetorMapper {

    Setor toSetor(SetorRequest setorRequest);

    SetorResponse toSetorResponse(Setor setor);

    List<SetorResponse> toSetorResponseList(List<Setor> setores);

    void updateSetor(SetorRequest setorRequest, @MappingTarget Setor setor);

}
