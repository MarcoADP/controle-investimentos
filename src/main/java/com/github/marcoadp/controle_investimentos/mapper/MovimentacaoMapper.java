package com.github.marcoadp.controle_investimentos.mapper;

import com.github.marcoadp.controle_investimentos.dto.request.MovimentacaoRequest;
import com.github.marcoadp.controle_investimentos.dto.response.MovimentacaoResponse;
import com.github.marcoadp.controle_investimentos.entity.Movimentacao;
import com.github.marcoadp.controle_investimentos.enums.TipoAtivoEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.math.BigDecimal;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MovimentacaoMapper {

    @Mapping(source = "tipoAtivo", target = "tipoAtivo", qualifiedByName = "definirTipoAtivo")
    @Mapping(target = "valorTotal", source = "movimentacaoRequest", qualifiedByName = "calcularValorTotal")
    Movimentacao toMovimentacao(MovimentacaoRequest movimentacaoRequest);

    @Mapping(source = "tipoAtivo", target = "tipoAtivo", qualifiedByName = "getTipoAtivoDescricao")
    MovimentacaoResponse toMovimentacaoResponse(Movimentacao movimentacao);

    @Named("definirTipoAtivo")
    default TipoAtivoEnum definirTipoAtivo(String descricao) {
        return TipoAtivoEnum.getTipoAtivoEnumByDescricao(descricao);
    }

    @Named("getTipoAtivoDescricao")
    default String getTipoAtivoDescricao(TipoAtivoEnum tipoAtivo) {
        return tipoAtivo != null ? tipoAtivo.getDescricao() : null;
    }

    @Named("calcularValorTotal")
    default BigDecimal calcularValorTotal(MovimentacaoRequest request) {
        return request.valorUnitario().multiply(request.quantidade());
    }

}
