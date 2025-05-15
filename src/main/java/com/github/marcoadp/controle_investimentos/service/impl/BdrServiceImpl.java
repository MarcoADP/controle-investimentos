package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.dto.request.BdrRequest;
import com.github.marcoadp.controle_investimentos.entity.Bdr;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.mapper.BdrMapper;
import com.github.marcoadp.controle_investimentos.repository.BdrRepository;
import com.github.marcoadp.controle_investimentos.service.BdrService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BdrServiceImpl implements BdrService {

    private final BdrRepository bdrRepository;
    private final BdrMapper bdrMapper;

    @Override
    public Bdr criar(BdrRequest bdrRequest) {
        var bdr = bdrMapper.toBdr(bdrRequest);
        return bdrRepository.save(bdr);
    }

    @Override
    public Bdr atualizar(Long id, BdrRequest bdrRequest) {
        var bdr = bdrRepository.findById(id).orElseThrow(() -> new NotFoundException("Bdr", id));
        bdrMapper.updateBdr(bdrRequest, bdr);
        return bdrRepository.save(bdr);
    }

    @Override
    public Bdr buscarPeloId(Long id) {
        return bdrRepository.findById(id).orElseThrow(() -> new NotFoundException("Bdr", id));
    }

    @Override
    public Optional<Bdr> buscarPeloCodigo(String codigo) {
        return bdrRepository.findFirstByCodigo(codigo);
    }

    @Override
    public List<Bdr> buscarPeloSetor(String setor) {
        return bdrRepository.findBySetor(setor);
    }

    @Override
    public List<Bdr> listar() {
        return bdrRepository.findAll();
    }

    @Override
    public void remover(Long id) {
        bdrRepository.deleteById(id);
    }
}
