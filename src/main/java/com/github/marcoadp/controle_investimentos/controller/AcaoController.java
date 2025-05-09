package com.github.marcoadp.controle_investimentos.controller;

import com.github.marcoadp.controle_investimentos.dto.request.AcaoRequest;
import com.github.marcoadp.controle_investimentos.dto.response.AcaoResponse;
import com.github.marcoadp.controle_investimentos.mapper.AcaoMapper;
import com.github.marcoadp.controle_investimentos.service.AcaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acao")
@RequiredArgsConstructor
public class AcaoController {

    private final AcaoService acaoService;
    private final AcaoMapper acaoMapper;

    @PostMapping
    public AcaoResponse criar(@RequestBody AcaoRequest acaoRequest) {
        var acao = acaoService.criar(acaoRequest);
        return acaoMapper.toAcaoResponse(acao);
    }

    @PutMapping("/{id}")
    public AcaoResponse atualizar(@PathVariable Long id, @RequestBody AcaoRequest acaoRequest) {
        var acao = acaoService.atualizar(id, acaoRequest);
        return acaoMapper.toAcaoResponse(acao);
    }

    @GetMapping()
    public List<AcaoResponse> listar() {
        var acaoes = acaoService.listar();
        return acaoMapper.toAcaoResponseList(acaoes);
    }

    @GetMapping("/{id}")
    public AcaoResponse buscarPeloId(@PathVariable Long id) {
        var acao = acaoService.buscarPeloId(id);
        return acaoMapper.toAcaoResponse(acao);
    }

    @GetMapping("/codigo/{codigo}")
    public AcaoResponse buscarPeloCodigo(@PathVariable String codigo) {
        var acao = acaoService.buscarPeloCodigo(codigo);
        return acaoMapper.toAcaoResponse(acao);
    }

    @GetMapping("/setor/{setorId}")
    public List<AcaoResponse> buscarPeloSetor(@PathVariable Long setorId) {
        var acoes = acaoService.buscarPeloSetor(setorId);
        return acaoMapper.toAcaoResponseList(acoes);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        acaoService.remover(id);
    }



}

