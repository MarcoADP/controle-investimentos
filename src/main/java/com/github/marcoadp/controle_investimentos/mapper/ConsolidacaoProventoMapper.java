package com.github.marcoadp.controle_investimentos.mapper;

import com.github.marcoadp.controle_investimentos.dto.response.ConsolidacaoProventoResponse;
import com.github.marcoadp.controle_investimentos.entity.ConsolidacaoProvento;
import com.github.marcoadp.controle_investimentos.enums.TipoAtivoEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ConsolidacaoProventoMapper {

    @Mapping(source = "tipoAtivo", target = "tipoAtivo", qualifiedByName = "getTipoAtivoDescricao")
    ConsolidacaoProventoResponse toConsolidacaoProventoResponse(ConsolidacaoProvento consolidacao);

    @Named("getTipoAtivoDescricao")
    default String getTipoAtivoDescricao(TipoAtivoEnum tipoAtivo) {
        return tipoAtivo != null ? tipoAtivo.getDescricao() : null;
    }

}
