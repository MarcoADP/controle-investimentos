package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.dto.request.AcaoRequest;
import com.github.marcoadp.controle_investimentos.entity.Acao;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.mapper.AcaoMapper;
import com.github.marcoadp.controle_investimentos.repository.AcaoRepository;
import com.github.marcoadp.controle_investimentos.service.AcaoService;
import com.github.marcoadp.controle_investimentos.service.SetorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AcaoServiceImpl implements AcaoService {

    private final AcaoRepository acaoRepository;
    private final AcaoMapper acaoMapper;
    private final SetorService setorService;

    @Override
    public Acao criar(AcaoRequest acaoRequest) {
        var acao = acaoMapper.toAcao(acaoRequest);
        acao.setSetor(setorService.buscarPeloId(acaoRequest.setorId()));
        return acaoRepository.save(acao);
    }

    @Override
    public Acao atualizar(Long id, AcaoRequest acaoRequest) {
        var acao = acaoRepository.findById(id).orElseThrow(() -> new NotFoundException("Acao", id));
        if (acaoRequest.setorId() != null) {
            acao.setSetor(setorService.buscarPeloId(acaoRequest.setorId()));
        }
        acaoMapper.updateAcao(acaoRequest, acao);
        return acaoRepository.save(acao);
    }

    @Override
    public Acao buscarPeloId(Long id) {
        return acaoRepository.findById(id).orElseThrow(() -> new NotFoundException("Acao", id));
    }

    @Override
    public Acao buscarPeloCodigo(String codigo) {
        return acaoRepository.findFirstByCodigo(codigo).orElseThrow(() -> new NotFoundException("Acao", codigo));
    }

    @Override
    public List<Acao> buscarPeloSetor(Long setorId) {
        return acaoRepository.findBySetor(setorId);
    }

    @Override
    public List<Acao> listar() {
        return acaoRepository.findAll();
    }

    @Override
    public void remover(Long id) {
        acaoRepository.deleteById(id);
    }
}
