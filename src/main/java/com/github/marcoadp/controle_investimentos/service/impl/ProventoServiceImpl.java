package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.dto.request.ProventoRequest;
import com.github.marcoadp.controle_investimentos.entity.Provento;
import com.github.marcoadp.controle_investimentos.enums.TipoProventoEnum;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.mapper.ProventoMapper;
import com.github.marcoadp.controle_investimentos.repository.ProventoRepository;
import com.github.marcoadp.controle_investimentos.service.ProventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProventoServiceImpl implements ProventoService {

    private final ProventoRepository proventoRepository;
    private final ProventoMapper proventoMapper;

    @Override
    public Provento criar(ProventoRequest proventoRequest) {
        var provento = proventoMapper.toProvento(proventoRequest);
        return proventoRepository.save(provento);
    }

    @Override
    public List<Provento> criarEmLote(List<ProventoRequest> proventoRequests) {
        var proventos = proventoRequests.stream().map(proventoMapper::toProvento).toList();
        return proventoRepository.saveAll(proventos);
    }

    @Override
    public Provento buscarPeloId(Long id) {
        return proventoRepository.findById(id).orElseThrow(() -> new NotFoundException("Provento", id));
    }

    @Override
    public List<Provento> buscarPeloCodigo(String codigo) {
        return proventoRepository.findByCodigo(codigo);
    }

    @Override
    public List<Provento> buscarPeloTipoProvento(String tipoProvento) {
        var tipoProventoEnum = TipoProventoEnum.getTipoProventoEnumByDescricao(tipoProvento);
        return proventoRepository.findByTipoProvento(tipoProventoEnum);
    }

    @Override
    public void remover(Long id) {
        proventoRepository.deleteById(id);
    }
}
