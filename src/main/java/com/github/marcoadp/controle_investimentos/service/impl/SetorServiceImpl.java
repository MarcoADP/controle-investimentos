package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.dto.request.SetorRequest;
import com.github.marcoadp.controle_investimentos.entity.Setor;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.mapper.SetorMapper;
import com.github.marcoadp.controle_investimentos.repository.SetorRepository;
import com.github.marcoadp.controle_investimentos.service.SetorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SetorServiceImpl implements SetorService {

    private final SetorRepository setorRepository;
    private final SetorMapper setorMapper;

    @Override
    public Setor criar(SetorRequest setorRequest) {
        var setor = setorMapper.toSetor(setorRequest);
        return setorRepository.save(setor);
    }

    @Override
    public Setor atualizar(Long id, SetorRequest setorRequest) {
        var setor = setorRepository.findById(id).orElseThrow(() -> new NotFoundException("Setor", id));
        setorMapper.updateSetor(setorRequest, setor);
        return setorRepository.save(setor);
    }

    @Override
    public Setor buscarPeloId(Long id) {
        return setorRepository.findById(id).orElseThrow(() -> new NotFoundException("Setor", id));
    }

    @Override
    public List<Setor> listar() {
        return setorRepository.findAll();
    }

    @Override
    public void remover(Long id) {
        setorRepository.deleteById(id);
    }
}
