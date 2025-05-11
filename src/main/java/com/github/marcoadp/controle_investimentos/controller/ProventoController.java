package com.github.marcoadp.controle_investimentos.controller;

import com.github.marcoadp.controle_investimentos.dto.request.ProventoRequest;
import com.github.marcoadp.controle_investimentos.dto.response.ProventoResponse;
import com.github.marcoadp.controle_investimentos.mapper.ProventoMapper;
import com.github.marcoadp.controle_investimentos.service.ProventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/provento")
@RequiredArgsConstructor
public class ProventoController {

    private final ProventoService proventoService;
    private final ProventoMapper proventoMapper;

    @PostMapping
    public ProventoResponse criar(@RequestBody ProventoRequest proventoRequest) {
        var provento = proventoService.criar(proventoRequest);
        return proventoMapper.toProventoResponse(provento);
    }

    @PostMapping("/lote")
    public List<ProventoResponse> criarEmLote(@RequestBody List<ProventoRequest> proventoRequests) {
        var proventos = proventoService.criarEmLote(proventoRequests);
        return proventos.stream().map(proventoMapper::toProventoResponse).toList();
    }

    @GetMapping("/{id}")
    public ProventoResponse buscarPeloId(@PathVariable Long id) {
        var provento = proventoService.buscarPeloId(id);
        return proventoMapper.toProventoResponse(provento);
    }

    @GetMapping("/codigo/{codigo}")
    public List<ProventoResponse> buscarPeloCodigo(@PathVariable String codigo) {
        var proventos = proventoService.buscarPeloCodigo(codigo);
        return proventos.stream().map(proventoMapper::toProventoResponse).toList();
    }

    @GetMapping("/tipo/{tipoProvento}")
    public List<ProventoResponse> buscarPeloTipoProvento(@PathVariable String tipoProvento) {
        var proventos = proventoService.buscarPeloTipoProvento(tipoProvento);
        return proventos.stream().map(proventoMapper::toProventoResponse).toList();
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        proventoService.remover(id);
    }



}

