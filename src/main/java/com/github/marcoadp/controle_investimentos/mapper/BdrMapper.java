package com.github.marcoadp.controle_investimentos.mapper;

import com.github.marcoadp.controle_investimentos.dto.request.BdrRequest;
import com.github.marcoadp.controle_investimentos.dto.response.BdrResponse;
import com.github.marcoadp.controle_investimentos.entity.Bdr;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BdrMapper {

    Bdr toBdr(BdrRequest bdrRequest);

    BdrResponse toBdrResponse(Bdr bdr);

    void updateBdr(BdrRequest bdrRequest, @MappingTarget Bdr bdr);

}
