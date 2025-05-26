package com.github.marcoadp.controle_investimentos.service;

import com.github.marcoadp.controle_investimentos.dto.response.CarteiraSimplificadaResponse;
import com.github.marcoadp.controle_investimentos.dto.response.DadosResumoResponse;

public interface CarteiraDadosService {

    CarteiraSimplificadaResponse buscarCarteiraSimplificada(Long carteiraId);

    DadosResumoResponse buscarResumo(Long carteiraId);
}
