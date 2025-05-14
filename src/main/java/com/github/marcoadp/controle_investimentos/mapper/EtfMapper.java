package com.github.marcoadp.controle_investimentos.mapper;

import com.github.marcoadp.controle_investimentos.dto.request.EtfRequest;
import com.github.marcoadp.controle_investimentos.dto.response.EtfResponse;
import com.github.marcoadp.controle_investimentos.entity.Etf;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EtfMapper {

    Etf toEtf(EtfRequest etfRequest);

    EtfResponse toEtfResponse(Etf etf);

    void updateEtf(EtfRequest etfRequest, @MappingTarget Etf etf);

}
