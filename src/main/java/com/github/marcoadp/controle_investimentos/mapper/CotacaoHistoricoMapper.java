package com.github.marcoadp.controle_investimentos.mapper;

import com.github.marcoadp.controle_investimentos.dto.response.CotacaoHistoricoResponse;
import com.github.marcoadp.controle_investimentos.entity.CotacaoHistorico;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CotacaoHistoricoMapper {

    CotacaoHistoricoResponse toCotacaoHistoricoResponse(CotacaoHistorico cotacao);

}
