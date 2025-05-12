package com.github.marcoadp.controle_investimentos.mapper;

import com.github.marcoadp.controle_investimentos.dto.response.ConsolidacaoProventoResponse;
import com.github.marcoadp.controle_investimentos.entity.ConsolidacaoProvento;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ConsolidacaoProventoMapper {

    ConsolidacaoProventoResponse toConsolidacaoProventoResponse(ConsolidacaoProvento consolidacao);

}
