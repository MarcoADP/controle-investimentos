package com.github.marcoadp.controle_investimentos.client;

import com.github.marcoadp.controle_investimentos.client.response.BrapiCotacaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "usuarioClient", url = "${brapi.url}")
public interface BrapiClient {

    @GetMapping("/api/quote/{codigo}")
    BrapiCotacaoResponse buscarPorCotacao(@PathVariable("codigo") String codigo, @RequestParam("token") String token);

}
