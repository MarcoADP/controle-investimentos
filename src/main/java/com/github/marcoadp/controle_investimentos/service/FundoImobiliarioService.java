package com.github.marcoadp.controle_investimentos.service;

import com.github.marcoadp.controle_investimentos.dto.request.FundoImobiliarioRequest;
import com.github.marcoadp.controle_investimentos.entity.FundoImobiliario;

import java.util.List;
import java.util.Optional;

public interface FundoImobiliarioService {

    FundoImobiliario criar(FundoImobiliarioRequest request);

    FundoImobiliario atualizar(Long id, FundoImobiliarioRequest request);

    FundoImobiliario buscarPeloId(Long id);

    Optional<FundoImobiliario> buscarPeloCodigo(String codigo);

    List<FundoImobiliario> buscar(String codigo, String tipo, String segmento);

    void remover(Long id);

}
