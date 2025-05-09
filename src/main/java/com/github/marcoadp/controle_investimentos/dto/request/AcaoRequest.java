package com.github.marcoadp.controle_investimentos.dto.request;

public record AcaoRequest(String nome, String codigo, String cnpj, Long setorId) {
}
