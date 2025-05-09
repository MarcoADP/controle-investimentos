package com.github.marcoadp.controle_investimentos.mapper;

import com.github.marcoadp.controle_investimentos.dto.request.CarteiraRequest;
import com.github.marcoadp.controle_investimentos.dto.response.CarteiraResponse;
import com.github.marcoadp.controle_investimentos.entity.Carteira;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CarteiraMapper {

    Carteira toCarteira(CarteiraRequest carteiraRequest);

    CarteiraResponse toCarteiraResponse(Carteira carteira);

}
