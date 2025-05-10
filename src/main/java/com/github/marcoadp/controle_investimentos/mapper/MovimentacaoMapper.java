package com.github.marcoadp.controle_investimentos.mapper;

import com.github.marcoadp.controle_investimentos.dto.request.MovimentacaoRequest;
import com.github.marcoadp.controle_investimentos.dto.response.MovimentacaoResponse;
import com.github.marcoadp.controle_investimentos.entity.Movimentacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.math.BigDecimal;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MovimentacaoMapper {

    @Mapping(target = "valorTotal", source = "movimentacaoRequest", qualifiedByName = "calcularValorTotal")
    Movimentacao toMovimentacao(MovimentacaoRequest movimentacaoRequest);

    MovimentacaoResponse toMovimentacaoResponse(Movimentacao movimentacao);

    @Named("calcularValorTotal")
    default BigDecimal calcularValorTotal(MovimentacaoRequest request) {
        return request.valorUnitario().multiply(request.quantidade());
    }

}
