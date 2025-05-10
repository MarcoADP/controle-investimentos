package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.dto.request.CarteiraRequest;
import com.github.marcoadp.controle_investimentos.entity.Carteira;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.mapper.CarteiraMapper;
import com.github.marcoadp.controle_investimentos.repository.CarteiraRepository;
import com.github.marcoadp.controle_investimentos.service.CarteiraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarteiraServiceImpl implements CarteiraService {

    private final CarteiraRepository carteiraRepository;
    private final CarteiraMapper carteiraMapper;

    @Override
    public Carteira criar(CarteiraRequest carteiraRequest) {
        var carteira = carteiraMapper.toCarteira(carteiraRequest);
        return carteiraRepository.save(carteira);
    }

    @Override
    public Carteira buscarPeloId(Long id) {
        return carteiraRepository.findById(id).orElseThrow(() -> new NotFoundException("Carteira", id));
    }

    @Override
    public void remover(Long id) {
        carteiraRepository.deleteById(id);
    }

    @Override
    public List<Carteira> listar() {
        return carteiraRepository.findAll();
    }
}
