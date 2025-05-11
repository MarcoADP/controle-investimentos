package com.github.marcoadp.controle_investimentos.mapper;

import com.github.marcoadp.controle_investimentos.dto.request.ProventoRequest;
import com.github.marcoadp.controle_investimentos.dto.response.ProventoResponse;
import com.github.marcoadp.controle_investimentos.entity.Provento;
import com.github.marcoadp.controle_investimentos.enums.TipoProventoEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProventoMapper {

    @Mapping(source = "tipoProvento", target = "tipoProvento", qualifiedByName = "definirTipoProvento")
    @Mapping(target = "valorMedio", source = "proventoRequest", qualifiedByName = "calcularValorMedio")
    Provento toProvento(ProventoRequest proventoRequest);

    @Mapping(source = "tipoProvento", target = "tipoProvento", qualifiedByName = "getTipoProventoDescricao")
    ProventoResponse toProventoResponse(Provento provento);

    @Named("definirTipoProvento")
    default TipoProventoEnum definirTipoProvento(String descricao) {
        return TipoProventoEnum.getTipoProventoEnumByDescricao(descricao);
    }

    @Named("getTipoProventoDescricao")
    default String getTipoProventoDescricao(TipoProventoEnum tipoProvento) {
        return tipoProvento != null ? tipoProvento.getDescricao() : null;
    }

    @Named("calcularValorMedio")
    default BigDecimal calcularValorMedio(ProventoRequest request) {
        return request.valorTotal().divide(BigDecimal.valueOf(request.quantidade()), 5, RoundingMode.HALF_UP);
    }

}
