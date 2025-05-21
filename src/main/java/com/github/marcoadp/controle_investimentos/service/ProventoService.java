package com.github.marcoadp.controle_investimentos.service;

import com.github.marcoadp.controle_investimentos.dto.request.ProventoRequest;
import com.github.marcoadp.controle_investimentos.entity.Provento;

import java.util.List;

public interface ProventoService {

    Provento criar(ProventoRequest request);

    Provento buscarPeloId(Long id);

    List<Provento> buscarPeloCodigo(String codigo);

    List<Provento> buscarPeloTipoProvento(String tipoProvento);

    List<Provento> buscarTodos();

    void remover(Long id);

    List<Provento> criarEmLote(List<ProventoRequest> proventoRequests);
}
