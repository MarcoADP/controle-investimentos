package com.github.marcoadp.controle_investimentos.service;

import com.github.marcoadp.controle_investimentos.dto.request.SetorRequest;
import com.github.marcoadp.controle_investimentos.entity.Setor;

import java.util.List;

public interface SetorService {

    Setor criar(SetorRequest request);

    Setor atualizar(Long id, SetorRequest request);

    Setor buscarPeloId(Long id);

    List<Setor> listar();

    void remover(Long id);

}
