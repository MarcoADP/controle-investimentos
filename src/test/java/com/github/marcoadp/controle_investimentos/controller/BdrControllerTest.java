package com.github.marcoadp.controle_investimentos.controller;

import com.github.marcoadp.controle_investimentos.handler.GlobalExceptionHandler;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.mapper.BdrMapper;
import com.github.marcoadp.controle_investimentos.service.BdrService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static com.github.marcoadp.controle_investimentos.stub.BdrStub.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BdrController.class)
@Import(GlobalExceptionHandler.class)
class BdrControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BdrService bdrService;

    @MockitoBean
    private BdrMapper bdrMapper;

    @Test
    void criar() throws Exception {

        var bdr = getBdr(1L);
        when(bdrService.criar(getBdrRequest())).thenReturn(bdr);
        when(bdrMapper.toBdrResponse(bdr)).thenReturn(getBdrResponse());

        mockMvc.perform(post("/bdr")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BDR_REQUEST_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("BDR"));

    }

    @Test
    void atualizar() throws Exception {
        var bdr = getBdr(1L);
        when(bdrService.atualizar(1L, getBdrRequest())).thenReturn(bdr);
        when(bdrMapper.toBdrResponse(bdr)).thenReturn(getBdrResponse());

        mockMvc.perform(put("/bdr/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BDR_REQUEST_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("BDR"));
    }

    @Test
    void listar() throws Exception {
        var bdr = getBdr(1L);
        when(bdrService.listar()).thenReturn(List.of(bdr));
        when(bdrMapper.toBdrResponse(bdr)).thenReturn(getBdrResponse());

        mockMvc.perform(get("/bdr"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nome").value("BDR"))
                .andExpect(jsonPath("$[0].setor").value("Setor"))
                .andExpect(jsonPath("$[0].cnpj").value("26492737000162"))
                .andExpect(jsonPath("$[0].codigo").value("BDRZ32"));
    }

    @Test
    void buscarPeloId() throws Exception {
        var bdr = getBdr(1L);
        when(bdrService.buscarPeloId(1L)).thenReturn(bdr);
        when(bdrMapper.toBdrResponse(bdr)).thenReturn(getBdrResponse());

        mockMvc.perform(get("/bdr/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("BDR"));
    }

    @Test
    void buscarPeloCodigo() throws Exception {
        var bdr = getBdr(1L);
        when(bdrService.buscarPeloCodigo(bdr.getCodigo())).thenReturn(Optional.of(bdr));
        when(bdrMapper.toBdrResponse(bdr)).thenReturn(getBdrResponse());

        mockMvc.perform(get("/bdr/codigo/BDRZ32"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("BDR"))
                .andExpect(jsonPath("$.setor").value("Setor"))
                .andExpect(jsonPath("$.cnpj").value("26492737000162"))
                .andExpect(jsonPath("$.codigo").value("BDRZ32"));
    }

    @Test
    void buscarPeloSetor() throws Exception {
        var bdr = getBdr(1L);
        when(bdrService.buscarPeloSetor("Setor")).thenReturn(List.of(bdr));
        when(bdrMapper.toBdrResponse(bdr)).thenReturn(getBdrResponse());

        mockMvc.perform(get("/bdr/setor/Setor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nome").value("BDR"))
                .andExpect(jsonPath("$[0].setor").value("Setor"))
                .andExpect(jsonPath("$[0].cnpj").value("26492737000162"))
                .andExpect(jsonPath("$[0].codigo").value("BDRZ32"));
    }

    @Test
    void buscarPeloIdErro() throws Exception {
        when(bdrService.buscarPeloId(1L)).thenThrow(new NotFoundException("Bdr", 1L));

        mockMvc.perform(get("/bdr/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.erro").value("NAO_ENCONTRADO"))
                .andExpect(jsonPath("$.mensagem").value("Bdr #1 não encontrado"))
                .andExpect(jsonPath("$.status").value("404"));
    }

    @Test
    void remover() throws Exception {
        mockMvc.perform(delete("/bdr/1"))
                .andExpect(status().isOk());
    }
}