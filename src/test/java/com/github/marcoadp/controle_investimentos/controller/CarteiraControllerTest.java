package com.github.marcoadp.controle_investimentos.controller;

import com.github.marcoadp.controle_investimentos.handler.GlobalExceptionHandler;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.mapper.CarteiraMapper;
import com.github.marcoadp.controle_investimentos.service.CarteiraService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.marcoadp.controle_investimentos.stub.CarteiraStub.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CarteiraController.class)
@Import(GlobalExceptionHandler.class)
class CarteiraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarteiraService carteiraService;

    @MockitoBean
    private CarteiraMapper carteiraMapper;

    @Test
    void criar() throws Exception {

        var carteira = getCarteira();
        when(carteiraService.criar(getCarteiraRequest())).thenReturn(carteira);
        when(carteiraMapper.toCarteiraResponse(carteira)).thenReturn(getCarteiraResponse());

        mockMvc.perform(post("/carteira")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CARTEIRA_REQUEST_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Carteira"))
                .andExpect(jsonPath("$.descricao").value("Carteira de Ativos"))
                .andExpect(jsonPath("$.ativos.length()").value(1))
                .andExpect(jsonPath("$.ativos[0].codigo").value("ACAO4"));

    }

    @Test
    void buscarPeloId() throws Exception {
        var carteira = getCarteira();
        when(carteiraService.buscarPeloId(1L)).thenReturn(carteira);
        when(carteiraMapper.toCarteiraResponse(carteira)).thenReturn(getCarteiraResponse());

        mockMvc.perform(get("/carteira/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Carteira"))
                .andExpect(jsonPath("$.descricao").value("Carteira de Ativos"))
                .andExpect(jsonPath("$.ativos.length()").value(1))
                .andExpect(jsonPath("$.ativos[0].codigo").value("ACAO4"));
    }

    @Test
    void buscarPeloIdErro() throws Exception {
        when(carteiraService.buscarPeloId(1L)).thenThrow(new NotFoundException("Carteira", 1L));

        mockMvc.perform(get("/carteira/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.erro").value("NAO_ENCONTRADO"))
                .andExpect(jsonPath("$.mensagem").value("Carteira #1 não encontrado"))
                .andExpect(jsonPath("$.status").value("404"));
    }

    @Test
    void remover() throws Exception {
        mockMvc.perform(delete("/carteira/1"))
                .andExpect(status().isOk());
    }
}