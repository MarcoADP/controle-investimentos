package com.github.marcoadp.controle_investimentos.controller;

import com.github.marcoadp.controle_investimentos.handler.GlobalExceptionHandler;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.mapper.AcaoMapper;
import com.github.marcoadp.controle_investimentos.service.AcaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static com.github.marcoadp.controle_investimentos.stub.AcaoStub.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AcaoController.class)
@Import(GlobalExceptionHandler.class)
class AcaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AcaoService acaoService;

    @MockitoBean
    private AcaoMapper acaoMapper;

    @Test
    void criar() throws Exception {

        var acao = getAcao();
        when(acaoService.criar(getAcaoRequest())).thenReturn(acao);
        when(acaoMapper.toAcaoResponse(acao)).thenReturn(getAcaoResponse());

        mockMvc.perform(post("/acao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ACAO_REQUEST_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Acao"));

    }

    @Test
    void atualizar() throws Exception {
        var acao = getAcao();
        when(acaoService.atualizar(1L, getAcaoRequest())).thenReturn(acao);
        when(acaoMapper.toAcaoResponse(acao)).thenReturn(getAcaoResponse());

        mockMvc.perform(put("/acao/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ACAO_REQUEST_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Acao"));
    }

    @Test
    void listar() throws Exception {
        var acoes = List.of(getAcao());
        when(acaoService.listar()).thenReturn(acoes);
        when(acaoMapper.toAcaoResponseList(acoes)).thenReturn(List.of(getAcaoResponse()));

        mockMvc.perform(get("/acao"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nome").value("Acao"))
                .andExpect(jsonPath("$[0].setor").value("Setor"))
                .andExpect(jsonPath("$[0].cnpj").value("26492737000162"))
                .andExpect(jsonPath("$[0].codigo").value("ACAO4"));
    }

    @Test
    void buscarPeloId() throws Exception {
        var acao = getAcao();
        when(acaoService.buscarPeloId(1L)).thenReturn(acao);
        when(acaoMapper.toAcaoResponse(acao)).thenReturn(getAcaoResponse());

        mockMvc.perform(get("/acao/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Acao"));
    }

    @Test
    void buscarPeloCodigo() throws Exception {
        var acao = getAcao();
        when(acaoService.buscarPeloCodigo(acao.getCodigo())).thenReturn(Optional.of(acao));
        when(acaoMapper.toAcaoResponse(acao)).thenReturn(getAcaoResponse());

        mockMvc.perform(get("/acao/codigo/ACAO4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Acao"))
                .andExpect(jsonPath("$.setor").value("Setor"))
                .andExpect(jsonPath("$.cnpj").value("26492737000162"))
                .andExpect(jsonPath("$.codigo").value("ACAO4"));
    }

    @Test
    void buscarPeloSetor() throws Exception {
        var acoes = List.of(getAcao());
        when(acaoService.buscarPeloSetor(1L)).thenReturn(acoes);
        when(acaoMapper.toAcaoResponseList(acoes)).thenReturn(List.of(getAcaoResponse()));

        mockMvc.perform(get("/acao/setor/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nome").value("Acao"))
                .andExpect(jsonPath("$[0].setor").value("Setor"))
                .andExpect(jsonPath("$[0].cnpj").value("26492737000162"))
                .andExpect(jsonPath("$[0].codigo").value("ACAO4"));
    }

    @Test
    void buscarPeloIdErro() throws Exception {
        when(acaoService.buscarPeloId(1L)).thenThrow(new NotFoundException("Acao", 1L));

        mockMvc.perform(get("/acao/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.erro").value("NAO_ENCONTRADO"))
                .andExpect(jsonPath("$.mensagem").value("Acao #1 não encontrado"))
                .andExpect(jsonPath("$.status").value("404"));
    }

    @Test
    void remover() throws Exception {
        mockMvc.perform(delete("/acao/1"))
                .andExpect(status().isOk());
    }
}