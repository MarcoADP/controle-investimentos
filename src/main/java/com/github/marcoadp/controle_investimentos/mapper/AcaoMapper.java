package com.github.marcoadp.controle_investimentos.mapper;

import com.github.marcoadp.controle_investimentos.dto.request.AcaoRequest;
import com.github.marcoadp.controle_investimentos.dto.response.AcaoResponse;
import com.github.marcoadp.controle_investimentos.entity.Acao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AcaoMapper {

    Acao toAcao(AcaoRequest acaoRequest);

    @Mapping(target = "setor", source = "setor.nome")
    AcaoResponse toAcaoResponse(Acao acao);

    @Mapping(target = "setor", source = "setor.nome")
    List<AcaoResponse> toAcaoResponseList(List<Acao> acoes);

    void updateAcao(AcaoRequest acaoRequest, @MappingTarget Acao acao);

}
