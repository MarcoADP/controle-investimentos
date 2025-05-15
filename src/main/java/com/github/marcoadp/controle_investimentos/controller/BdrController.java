package com.github.marcoadp.controle_investimentos.controller;

import com.github.marcoadp.controle_investimentos.dto.request.BdrRequest;
import com.github.marcoadp.controle_investimentos.dto.response.BdrResponse;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.mapper.BdrMapper;
import com.github.marcoadp.controle_investimentos.service.BdrService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bdr")
@RequiredArgsConstructor
public class BdrController {

    private final BdrService bdrService;
    private final BdrMapper bdrMapper;

    @PostMapping
    public BdrResponse criar(@RequestBody BdrRequest bdrRequest) {
        var bdr = bdrService.criar(bdrRequest);
        return bdrMapper.toBdrResponse(bdr);
    }

    @PutMapping("/{id}")
    public BdrResponse atualizar(@PathVariable Long id, @RequestBody BdrRequest bdrRequest) {
        var bdr = bdrService.atualizar(id, bdrRequest);
        return bdrMapper.toBdrResponse(bdr);
    }

    @GetMapping()
    public List<BdrResponse> listar() {
        var bdrs = bdrService.listar();
        return bdrs.stream().map(bdrMapper::toBdrResponse).toList();
    }

    @GetMapping("/{id}")
    public BdrResponse buscarPeloId(@PathVariable Long id) {
        var bdr = bdrService.buscarPeloId(id);
        return bdrMapper.toBdrResponse(bdr);
    }

    @GetMapping("/codigo/{codigo}")
    public BdrResponse buscarPeloCodigo(@PathVariable String codigo) {
        var bdr = bdrService.buscarPeloCodigo(codigo).orElseThrow(() -> new NotFoundException("Bdr", codigo));
        return bdrMapper.toBdrResponse(bdr);
    }

    @GetMapping("/setor/{setor}")
    public List<BdrResponse> buscarPeloSetor(@PathVariable String setor) {
        var bdrs = bdrService.buscarPeloSetor(setor);
        return bdrs.stream().map(bdrMapper::toBdrResponse).toList();
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        bdrService.remover(id);
    }



}

