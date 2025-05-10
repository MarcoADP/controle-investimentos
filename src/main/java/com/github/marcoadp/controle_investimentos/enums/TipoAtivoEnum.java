package com.github.marcoadp.controle_investimentos.enums;

import lombok.Getter;

public enum TipoAtivoEnum {

    ACAO("Ação"), FI("Fundo Imobiliário");

    @Getter
    private String descricao;

    TipoAtivoEnum(String descricao) {
        this.descricao = descricao;
    }
}
