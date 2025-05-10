package com.github.marcoadp.controle_investimentos.mapper;

import com.github.marcoadp.controle_investimentos.dto.response.ConsolidacaoResponse;
import com.github.marcoadp.controle_investimentos.entity.Consolidacao;
import com.github.marcoadp.controle_investimentos.enums.TipoAtivoEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ConsolidacaoMapper {

    @Mapping(source = "tipoAtivo", target = "tipoAtivo", qualifiedByName = "getTipoAtivoDescricao")
    ConsolidacaoResponse toConsolidacaoResponse(Consolidacao consolidacao);

    @Named("getTipoAtivoDescricao")
    default String getTipoAtivoDescricao(TipoAtivoEnum tipoAtivo) {
        return tipoAtivo != null ? tipoAtivo.getDescricao() : null;
    }

}
