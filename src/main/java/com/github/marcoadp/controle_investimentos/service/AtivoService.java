package com.github.marcoadp.controle_investimentos.service;

import com.github.marcoadp.controle_investimentos.dto.response.AtivoResponse;

public interface AtivoService {

    AtivoResponse buscarAtivo(String codigo);

}
