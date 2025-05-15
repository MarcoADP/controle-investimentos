package com.github.marcoadp.controle_investimentos.controller;

import com.github.marcoadp.controle_investimentos.dto.request.EtfRequest;
import com.github.marcoadp.controle_investimentos.dto.response.EtfResponse;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.mapper.EtfMapper;
import com.github.marcoadp.controle_investimentos.service.EtfService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/etf")
@RequiredArgsConstructor
public class EtfController {

    private final EtfService etfService;
    private final EtfMapper etfMapper;

    @PostMapping
    public EtfResponse criar(@RequestBody EtfRequest etfRequest) {
        var etf = etfService.criar(etfRequest);
        return etfMapper.toEtfResponse(etf);
    }

    @PutMapping("/{id}")
    public EtfResponse atualizar(@PathVariable Long id, @RequestBody EtfRequest etfRequest) {
        var etf = etfService.atualizar(id, etfRequest);
        return etfMapper.toEtfResponse(etf);
    }

    @GetMapping()
    public List<EtfResponse> listar() {
        var etfs = etfService.listar();
        return etfs.stream().map(etfMapper::toEtfResponse).toList();
    }

    @GetMapping("/{id}")
    public EtfResponse buscarPeloId(@PathVariable Long id) {
        var etf = etfService.buscarPeloId(id);
        return etfMapper.toEtfResponse(etf);
    }

    @GetMapping("/codigo/{codigo}")
    public EtfResponse buscarPeloCodigo(@PathVariable String codigo) {
        var etf = etfService.buscarPeloCodigo(codigo).orElseThrow(() -> new NotFoundException("Etf", codigo));
        return etfMapper.toEtfResponse(etf);
    }

    @GetMapping("/tipo/{tipo}")
    public List<EtfResponse> buscarPeloTipo(@PathVariable String tipo) {
        var etfs = etfService.buscarPeloTipo(tipo);
        return etfs.stream().map(etfMapper::toEtfResponse).toList();
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        etfService.remover(id);
    }



}

