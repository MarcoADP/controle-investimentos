package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.dto.request.EtfRequest;
import com.github.marcoadp.controle_investimentos.entity.Etf;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.mapper.EtfMapper;
import com.github.marcoadp.controle_investimentos.repository.EtfRepository;
import com.github.marcoadp.controle_investimentos.service.EtfService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EtfServiceImpl implements EtfService {

    private final EtfRepository etfRepository;
    private final EtfMapper etfMapper;

    @Override
    public Etf criar(EtfRequest etfRequest) {
        var etf = etfMapper.toEtf(etfRequest);
        return etfRepository.save(etf);
    }

    @Override
    public Etf atualizar(Long id, EtfRequest etfRequest) {
        var etf = etfRepository.findById(id).orElseThrow(() -> new NotFoundException("Etf", id));
        etfMapper.updateEtf(etfRequest, etf);
        return etfRepository.save(etf);
    }

    @Override
    public Etf buscarPeloId(Long id) {
        return etfRepository.findById(id).orElseThrow(() -> new NotFoundException("Etf", id));
    }

    @Override
    public Etf buscarPeloCodigo(String codigo) {
        return etfRepository.findFirstByCodigo(codigo).orElseThrow(() -> new NotFoundException("Etf", codigo));
    }

    @Override
    public List<Etf> buscarPeloTipo(String tipo) {
        return etfRepository.findByTipo(tipo);
    }

    @Override
    public List<Etf> listar() {
        return etfRepository.findAll();
    }

    @Override
    public void remover(Long id) {
        etfRepository.deleteById(id);
    }
}
