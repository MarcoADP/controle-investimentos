package com.github.marcoadp.controle_investimentos.controller;

import com.github.marcoadp.controle_investimentos.handler.GlobalExceptionHandler;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.mapper.MovimentacaoMapper;
import com.github.marcoadp.controle_investimentos.service.MovimentacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.github.marcoadp.controle_investimentos.stub.MovimentacaoStub.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MovimentacaoController.class)
@Import(GlobalExceptionHandler.class)
class MovimentacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovimentacaoService movimentacaoService;

    @MockitoBean
    private MovimentacaoMapper movimentacaoMapper;

    @Test
    void criar() throws Exception {

        var movimentacao = getMovimentacao();
        when(movimentacaoService.criar(getMovimentacaoRequest())).thenReturn(movimentacao);
        when(movimentacaoMapper.toMovimentacaoResponse(any())).thenReturn(getMovimentacaoResponse());

        mockMvc.perform(post("/movimentacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MOVIMENTACAO_REQUEST_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("2025-01-01"))
                .andExpect(jsonPath("$.operacao").value("ENTRADA"))
                .andExpect(jsonPath("$.codigo").value("ACAO4"))
                .andExpect(jsonPath("$.tipoAtivo").value("Ação"))
                .andExpect(jsonPath("$.quantidade").value("10"))
                .andExpect(jsonPath("$.valorUnitario").value("1"))
                .andExpect(jsonPath("$.valorTotal").value("10"));

    }

    @Test
    void buscarPeloId() throws Exception {
        var movimentacao = getMovimentacao();
        when(movimentacaoService.buscarPeloId(1L)).thenReturn(movimentacao);
        when(movimentacaoMapper.toMovimentacaoResponse(movimentacao)).thenReturn(getMovimentacaoResponse());

        mockMvc.perform(get("/movimentacao/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("2025-01-01"))
                .andExpect(jsonPath("$.operacao").value("ENTRADA"))
                .andExpect(jsonPath("$.codigo").value("ACAO4"))
                .andExpect(jsonPath("$.tipoAtivo").value("Ação"))
                .andExpect(jsonPath("$.quantidade").value("10"))
                .andExpect(jsonPath("$.valorUnitario").value("1"))
                .andExpect(jsonPath("$.valorTotal").value("10"));
    }

    @Test
    void buscarPeloCodigo() throws Exception {
        var movimentacao = getMovimentacao();
        when(movimentacaoService.buscarPeloCodigo("ACAO4")).thenReturn(List.of(movimentacao));
        when(movimentacaoMapper.toMovimentacaoResponse(movimentacao)).thenReturn(getMovimentacaoResponse());

        mockMvc.perform(get("/movimentacao/codigo/ACAO4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].data").value("2025-01-01"))
                .andExpect(jsonPath("$[0].operacao").value("ENTRADA"))
                .andExpect(jsonPath("$[0].codigo").value("ACAO4"))
                .andExpect(jsonPath("$[0].tipoAtivo").value("Ação"))
                .andExpect(jsonPath("$[0].quantidade").value("10"))
                .andExpect(jsonPath("$[0].valorUnitario").value("1"))
                .andExpect(jsonPath("$[0].valorTotal").value("10"));
    }

    @Test
    void buscarPeloIdErro() throws Exception {
        when(movimentacaoService.buscarPeloId(1L)).thenThrow(new NotFoundException("Movimentacao", 1L));

        mockMvc.perform(get("/movimentacao/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.erro").value("NAO_ENCONTRADO"))
                .andExpect(jsonPath("$.mensagem").value("Movimentacao #1 não encontrado"))
                .andExpect(jsonPath("$.status").value("404"));
    }

    @Test
    void remover() throws Exception {
        mockMvc.perform(delete("/movimentacao/1"))
                .andExpect(status().isOk());
    }
}