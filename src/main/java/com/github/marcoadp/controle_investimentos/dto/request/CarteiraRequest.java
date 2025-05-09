package com.github.marcoadp.controle_investimentos.dto.request;

import java.util.List;

public record CarteiraRequest(String nome, String descricao, List<CarteiraAtivoRequest> ativos) {
}
