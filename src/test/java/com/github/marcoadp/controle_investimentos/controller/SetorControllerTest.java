package com.github.marcoadp.controle_investimentos.controller;

import com.github.marcoadp.controle_investimentos.handler.GlobalExceptionHandler;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.mapper.SetorMapper;
import com.github.marcoadp.controle_investimentos.service.SetorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.github.marcoadp.controle_investimentos.stub.SetorStub.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SetorController.class)
@Import(GlobalExceptionHandler.class)
class SetorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SetorService setorService;

    @MockitoBean
    private SetorMapper setorMapper;

    @Test
    void criar() throws Exception {

        var setor = getSetor();
        when(setorService.criar(getSetorRequest())).thenReturn(setor);
        when(setorMapper.toSetorResponse(setor)).thenReturn(getSetorResponse());

        mockMvc.perform(post("/setor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Setor\"}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Setor"));

    }

    @Test
    void atualizar() throws Exception {
        var setor = getSetor();
        when(setorService.atualizar(1L, getSetorRequest())).thenReturn(setor);
        when(setorMapper.toSetorResponse(setor)).thenReturn(getSetorResponse());

        mockMvc.perform(put("/setor/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Setor\"}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Setor"));
    }

    @Test
    void listar() throws Exception {
        var setores = List.of(getSetor());
        when(setorService.listar()).thenReturn(setores);
        when(setorMapper.toSetorResponseList(setores)).thenReturn(List.of(getSetorResponse()));

        mockMvc.perform(get("/setor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nome").value("Setor"));
    }

    @Test
    void buscarPeloId() throws Exception {
        var setor = getSetor();
        when(setorService.buscarPeloId(1L)).thenReturn(setor);
        when(setorMapper.toSetorResponse(setor)).thenReturn(getSetorResponse());

        mockMvc.perform(get("/setor/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Setor"));
    }

    @Test
    void buscarPeloIdErro() throws Exception {
        var setor = getSetor();
        when(setorService.buscarPeloId(1L)).thenThrow(new NotFoundException("Setor", 1L));

        mockMvc.perform(get("/setor/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.erro").value("NAO_ENCONTRADO"))
                .andExpect(jsonPath("$.mensagem").value("Setor #1 não encontrado"))
                .andExpect(jsonPath("$.status").value("404"));
    }

    @Test
    void remover() throws Exception {
        mockMvc.perform(delete("/setor/1"))
                .andExpect(status().isOk());
    }
}