package com.github.marcoadp.controle_investimentos.enums;

import com.github.marcoadp.controle_investimentos.handler.EntradaInvalidaException;
import lombok.Getter;

import java.util.Arrays;

public enum TipoProventoEnum {

    JSCP("Juros sob Capital Próprio"), DIVIDENDOS("Dividendos"), RENDIMENTOS("Rendimentos");

    @Getter
    private String descricao;

    TipoProventoEnum(String descricao) {
        this.descricao = descricao;
    }

    public static TipoProventoEnum getTipoProventoEnumByDescricao(String descricao) {
        return Arrays.stream(TipoProventoEnum.values())
                .filter(e -> e.getDescricao().equalsIgnoreCase(descricao))
                .findFirst()
                .orElseThrow(() -> new EntradaInvalidaException("Tipo de Provento inválido: " + descricao));
    }

}
