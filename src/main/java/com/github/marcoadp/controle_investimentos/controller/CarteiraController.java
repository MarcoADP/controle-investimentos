package com.github.marcoadp.controle_investimentos.controller;

import com.github.marcoadp.controle_investimentos.dto.request.CarteiraRequest;
import com.github.marcoadp.controle_investimentos.dto.response.CarteiraResponse;
import com.github.marcoadp.controle_investimentos.mapper.CarteiraMapper;
import com.github.marcoadp.controle_investimentos.service.CarteiraService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carteira")
@RequiredArgsConstructor
public class CarteiraController {

    private final CarteiraService carteiraService;
    private final CarteiraMapper carteiraMapper;

    @PostMapping
    public CarteiraResponse criar(@RequestBody CarteiraRequest carteiraRequest) {
        var carteira = carteiraService.criar(carteiraRequest);
        return carteiraMapper.toCarteiraResponse(carteira);
    }

    @GetMapping("/{id}")
    public CarteiraResponse buscarPeloId(@PathVariable Long id) {
        var carteira = carteiraService.buscarPeloId(id);
        return carteiraMapper.toCarteiraResponse(carteira);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        carteiraService.remover(id);
    }



}

