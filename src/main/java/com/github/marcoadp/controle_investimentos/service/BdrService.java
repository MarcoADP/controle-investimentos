package com.github.marcoadp.controle_investimentos.service;

import com.github.marcoadp.controle_investimentos.dto.request.BdrRequest;
import com.github.marcoadp.controle_investimentos.entity.Bdr;

import java.util.List;
import java.util.Optional;

public interface BdrService {

    Bdr criar(BdrRequest request);

    Bdr atualizar(Long id, BdrRequest request);

    Bdr buscarPeloId(Long id);

    Optional<Bdr> buscarPeloCodigo(String codigo);

    List<Bdr> buscarPeloSetor(String setor);

    List<Bdr> listar();

    void remover(Long id);

}
