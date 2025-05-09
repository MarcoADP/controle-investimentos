package com.github.marcoadp.controle_investimentos.dto.response;

import java.util.List;

public record CarteiraResponse(String nome, String descricao, List<CarteiraAtivoResponse> ativos) {
}
