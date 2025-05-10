package com.github.marcoadp.controle_investimentos.enums;

import com.github.marcoadp.controle_investimentos.handler.EntradaInvalidaException;
import lombok.Getter;

import java.util.Arrays;

public enum TipoAtivoEnum {

    ACAO("Ação"), FI("Fundo Imobiliário");

    @Getter
    private String descricao;

    TipoAtivoEnum(String descricao) {
        this.descricao = descricao;
    }

    public static TipoAtivoEnum getTipoAtivoEnumByDescricao(String descricao) {
        return Arrays.stream(TipoAtivoEnum.values())
                .filter(e -> e.getDescricao().equalsIgnoreCase(descricao))
                .findFirst()
                .orElseThrow(() -> new EntradaInvalidaException("Tipo Ativo inválido: " + descricao));
    }
}
