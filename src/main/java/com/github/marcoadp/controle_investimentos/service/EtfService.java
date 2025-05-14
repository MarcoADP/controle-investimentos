package com.github.marcoadp.controle_investimentos.service;

import com.github.marcoadp.controle_investimentos.dto.request.EtfRequest;
import com.github.marcoadp.controle_investimentos.entity.Etf;

import java.util.List;

public interface EtfService {

    Etf criar(EtfRequest request);

    Etf atualizar(Long id, EtfRequest request);

    Etf buscarPeloId(Long id);

    Etf buscarPeloCodigo(String codigo);

    List<Etf> buscarPeloTipo(String tipo);

    List<Etf> listar();

    void remover(Long id);

}
