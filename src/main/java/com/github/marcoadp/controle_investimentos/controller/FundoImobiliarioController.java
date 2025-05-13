package com.github.marcoadp.controle_investimentos.controller;

import com.github.marcoadp.controle_investimentos.dto.request.FundoImobiliarioRequest;
import com.github.marcoadp.controle_investimentos.dto.response.FundoImobiliarioResponse;
import com.github.marcoadp.controle_investimentos.mapper.FundoImobiliarioMapper;
import com.github.marcoadp.controle_investimentos.service.FundoImobiliarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fundo-imobiliario")
@RequiredArgsConstructor
public class FundoImobiliarioController {

    private final FundoImobiliarioService fundoImobiliarioService;
    private final FundoImobiliarioMapper fundoImobiliarioMapper;

    @PostMapping
    public FundoImobiliarioResponse criar(@RequestBody FundoImobiliarioRequest fundoImobiliarioRequest) {
        var fundoImobiliario = fundoImobiliarioService.criar(fundoImobiliarioRequest);
        return fundoImobiliarioMapper.toFundoImobiliarioResponse(fundoImobiliario);
    }

    @PutMapping("/{id}")
    public FundoImobiliarioResponse atualizar(@PathVariable Long id, @RequestBody FundoImobiliarioRequest fundoImobiliarioRequest) {
        var fundoImobiliario = fundoImobiliarioService.atualizar(id, fundoImobiliarioRequest);
        return fundoImobiliarioMapper.toFundoImobiliarioResponse(fundoImobiliario);
    }

    @GetMapping()
    public List<FundoImobiliarioResponse> buscar(@RequestParam(required = false) String codigo,
                                                 @RequestParam(required = false) String tipo,
                                                 @RequestParam(required = false) String segmento
     ) {
        var fundoImobiliarios = fundoImobiliarioService.buscar(codigo, tipo, segmento);
        return fundoImobiliarios.stream().map(fundoImobiliarioMapper::toFundoImobiliarioResponse).toList();
    }

    @GetMapping("/{id}")
    public FundoImobiliarioResponse buscarPeloId(@PathVariable Long id) {
        var fundoImobiliario = fundoImobiliarioService.buscarPeloId(id);
        return fundoImobiliarioMapper.toFundoImobiliarioResponse(fundoImobiliario);
    }

    @GetMapping("/codigo/{codigo}")
    public FundoImobiliarioResponse buscarPeloCodigo(@PathVariable String codigo) {
        var fundoImobiliario = fundoImobiliarioService.buscarPeloCodigo(codigo);
        return fundoImobiliarioMapper.toFundoImobiliarioResponse(fundoImobiliario);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        fundoImobiliarioService.remover(id);
    }



}

