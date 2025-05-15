package com.github.marcoadp.controle_investimentos.service;

import com.github.marcoadp.controle_investimentos.dto.request.AcaoRequest;
import com.github.marcoadp.controle_investimentos.entity.Acao;

import java.util.List;
import java.util.Optional;

public interface AcaoService {

    Acao criar(AcaoRequest request);

    Acao atualizar(Long id, AcaoRequest request);

    Acao buscarPeloId(Long id);

    Optional<Acao> buscarPeloCodigo(String codigo);

    List<Acao> buscarPeloSetor(Long setorId);

    List<Acao> listar();

    void remover(Long id);

}
