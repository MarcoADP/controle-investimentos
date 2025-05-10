package com.github.marcoadp.controle_investimentos.enums;

public enum TipoAtivoEnum {

    ACAO("Ação"), FI("Fundo Imobiliário");

    private String descricao;

    TipoAtivoEnum(String descricao) {
        this.descricao = descricao;
    }
}
