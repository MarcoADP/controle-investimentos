package com.github.marcoadp.controle_investimentos.handler;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String classe, Long id) {
        super("%s #%s não encontrado".formatted(classe, id));
    }

    public NotFoundException(String classe, String descricao) {
        super("%s: %s não encontrado".formatted(classe, descricao));
    }

}
