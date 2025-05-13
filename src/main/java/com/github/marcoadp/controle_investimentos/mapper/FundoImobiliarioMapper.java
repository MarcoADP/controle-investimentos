package com.github.marcoadp.controle_investimentos.mapper;

import com.github.marcoadp.controle_investimentos.dto.request.FundoImobiliarioRequest;
import com.github.marcoadp.controle_investimentos.dto.response.FundoImobiliarioResponse;
import com.github.marcoadp.controle_investimentos.entity.FundoImobiliario;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FundoImobiliarioMapper {

    FundoImobiliario toFundoImobiliario(FundoImobiliarioRequest fundoImobiliarioRequest);

    FundoImobiliarioResponse toFundoImobiliarioResponse(FundoImobiliario fundoImobiliario);

    void updateFundoImobiliario(FundoImobiliarioRequest fundoImobiliarioRequest, @MappingTarget FundoImobiliario fundoImobiliario);

}
