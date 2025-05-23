package com.github.marcoadp.controle_investimentos.service;

import com.github.marcoadp.controle_investimentos.dto.request.CarteiraRequest;
import com.github.marcoadp.controle_investimentos.entity.Carteira;

import java.util.List;

public interface CarteiraService {

    Carteira criar(CarteiraRequest request);

    Carteira buscarPeloId(Long id);

    void remover(Long id);

    List<Carteira> listar();
}
