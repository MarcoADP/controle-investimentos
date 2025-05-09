package com.github.marcoadp.controle_investimentos.controller;

import com.github.marcoadp.controle_investimentos.dto.request.SetorRequest;
import com.github.marcoadp.controle_investimentos.dto.response.SetorResponse;
import com.github.marcoadp.controle_investimentos.mapper.SetorMapper;
import com.github.marcoadp.controle_investimentos.service.SetorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setor")
@RequiredArgsConstructor
public class SetorController {

    private final SetorService setorService;
    private final SetorMapper setorMapper;

    @PostMapping
    public SetorResponse criar(@RequestBody SetorRequest setorRequest) {
        var setor = setorService.criar(setorRequest);
        return setorMapper.toSetorResponse(setor);
    }

    @PutMapping("/{id}")
    public SetorResponse atualizar(@PathVariable Long id, @RequestBody SetorRequest setorRequest) {
        var setor = setorService.atualizar(id, setorRequest);
        return setorMapper.toSetorResponse(setor);
    }

    @GetMapping()
    public List<SetorResponse> listar() {
        var setores = setorService.listar();
        return setorMapper.toSetorResponseList(setores);
    }

    @GetMapping("/{id}")
    public SetorResponse buscarPeloId(@PathVariable Long id) {
        var setor = setorService.buscarPeloId(id);
        return setorMapper.toSetorResponse(setor);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        setorService.remover(id);
    }



}

