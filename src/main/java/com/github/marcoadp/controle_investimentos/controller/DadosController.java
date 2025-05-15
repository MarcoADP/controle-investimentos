package com.github.marcoadp.controle_investimentos.controller;

import com.github.marcoadp.controle_investimentos.dto.response.CarteiraSimplificadaResponse;
import com.github.marcoadp.controle_investimentos.service.DadosService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dados")
@RequiredArgsConstructor
public class DadosController {

    private final DadosService dadosService;

    @GetMapping("/carteira-simplificada/{id}")
    public CarteiraSimplificadaResponse buscarPeloId(@PathVariable Long id) {
        return dadosService.buscarCarteiraSimplificada(id);
    }





}

