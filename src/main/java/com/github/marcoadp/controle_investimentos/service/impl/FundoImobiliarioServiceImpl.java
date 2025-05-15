package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.dto.request.FundoImobiliarioRequest;
import com.github.marcoadp.controle_investimentos.entity.FundoImobiliario;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.mapper.FundoImobiliarioMapper;
import com.github.marcoadp.controle_investimentos.repository.FundoImobiliarioRepository;
import com.github.marcoadp.controle_investimentos.repository.spec.FundoImobiliarioSpec;
import com.github.marcoadp.controle_investimentos.service.FundoImobiliarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FundoImobiliarioServiceImpl implements FundoImobiliarioService {

    private final FundoImobiliarioRepository fundoImobiliarioRepository;
    private final FundoImobiliarioMapper fundoImobiliarioMapper;

    @Override
    public FundoImobiliario criar(FundoImobiliarioRequest fundoImobiliarioRequest) {
        var fundoImobiliario = fundoImobiliarioMapper.toFundoImobiliario(fundoImobiliarioRequest);
        return fundoImobiliarioRepository.save(fundoImobiliario);
    }

    @Override
    public FundoImobiliario atualizar(Long id, FundoImobiliarioRequest fundoImobiliarioRequest) {
        var fundoImobiliario = fundoImobiliarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Fundo Imobiliário", id));
        fundoImobiliarioMapper.updateFundoImobiliario(fundoImobiliarioRequest, fundoImobiliario);
        return fundoImobiliarioRepository.save(fundoImobiliario);
    }

    @Override
    public FundoImobiliario buscarPeloId(Long id) {
        return fundoImobiliarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Fundo Imobiliário", id));
    }

    @Override
    public Optional<FundoImobiliario> buscarPeloCodigo(String codigo) {
        return fundoImobiliarioRepository.findFirstByCodigo(codigo);
    }

    @Override
    public List<FundoImobiliario> buscar(String codigo, String tipo, String segmento) {
        var spec = FundoImobiliarioSpec.where(codigo, tipo, segmento);
        return fundoImobiliarioRepository.findAll(spec);
    }

    @Override
    public void remover(Long id) {
        fundoImobiliarioRepository.deleteById(id);
    }
}
