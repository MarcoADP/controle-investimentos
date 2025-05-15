package com.github.marcoadp.controle_investimentos.service;

import com.github.marcoadp.controle_investimentos.dto.response.CarteiraSimplificadaResponse;

public interface DadosService {

    CarteiraSimplificadaResponse buscarCarteiraSimplificada(Long carteiraId);

}
