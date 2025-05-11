package com.github.marcoadp.controle_investimentos.controller;

import com.github.marcoadp.controle_investimentos.handler.GlobalExceptionHandler;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.mapper.ProventoMapper;
import com.github.marcoadp.controle_investimentos.service.ProventoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.github.marcoadp.controle_investimentos.stub.ProventoStub.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProventoController.class)
@Import(GlobalExceptionHandler.class)
class ProventoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProventoService proventoService;

    @MockitoBean
    private ProventoMapper proventoMapper;

    @Test
    void criar() throws Exception {

        var provento = getProvento();
        when(proventoService.criar(getProventoRequest())).thenReturn(provento);
        when(proventoMapper.toProventoResponse(any())).thenReturn(getProventoResponse());

        mockMvc.perform(post("/provento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PROVENTO_REQUEST_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dataPagamento").value("2025-01-01"))
                .andExpect(jsonPath("$.tipoProvento").value("Dividendos"))
                .andExpect(jsonPath("$.codigo").value("ACAO4"))
                .andExpect(jsonPath("$.quantidade").value("10"))
                .andExpect(jsonPath("$.valorTotal").value("10"))
                .andExpect(jsonPath("$.valorMedio").value("1.0"));

    }

    @Test
    void criarEmLote() throws Exception {

        var provento = getProvento();
        when(proventoService.criarEmLote(List.of(getProventoRequest()))).thenReturn(List.of(provento));
        when(proventoMapper.toProventoResponse(any())).thenReturn(getProventoResponse());

        var r = mockMvc.perform(post("/provento/lote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PROVENTO_REQUEST_LOTE_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].dataPagamento").value("2025-01-01"))
                .andExpect(jsonPath("$[0].tipoProvento").value("Dividendos"))
                .andExpect(jsonPath("$[0].codigo").value("ACAO4"))
                .andExpect(jsonPath("$[0].quantidade").value("10"))
                .andExpect(jsonPath("$[0].valorTotal").value("10"))
                .andExpect(jsonPath("$[0].valorMedio").value("1.0"))
                .andReturn();
        var a = 1;

    }

    @Test
    void buscarPeloId() throws Exception {
        var provento = getProvento();
        when(proventoService.buscarPeloId(1L)).thenReturn(provento);
        when(proventoMapper.toProventoResponse(provento)).thenReturn(getProventoResponse());

        mockMvc.perform(get("/provento/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dataPagamento").value("2025-01-01"))
                .andExpect(jsonPath("$.tipoProvento").value("Dividendos"))
                .andExpect(jsonPath("$.codigo").value("ACAO4"))
                .andExpect(jsonPath("$.quantidade").value("10"))
                .andExpect(jsonPath("$.valorTotal").value("10"))
                .andExpect(jsonPath("$.valorMedio").value("1.0"));
    }

    @Test
    void buscarPeloCodigo() throws Exception {
        var provento = getProvento();
        when(proventoService.buscarPeloCodigo("ACAO4")).thenReturn(List.of(provento));
        when(proventoMapper.toProventoResponse(provento)).thenReturn(getProventoResponse());

        mockMvc.perform(get("/provento/codigo/ACAO4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dataPagamento").value("2025-01-01"))
                .andExpect(jsonPath("$[0].tipoProvento").value("Dividendos"))
                .andExpect(jsonPath("$[0].codigo").value("ACAO4"))
                .andExpect(jsonPath("$[0].quantidade").value("10"))
                .andExpect(jsonPath("$[0].valorTotal").value("10"))
                .andExpect(jsonPath("$[0].valorMedio").value("1.0"));
    }

    @Test
    void buscarPeloTipoProvento() throws Exception {
        var provento = getProvento();
        when(proventoService.buscarPeloTipoProvento("Dividendos")).thenReturn(List.of(provento));
        when(proventoMapper.toProventoResponse(provento)).thenReturn(getProventoResponse());

        mockMvc.perform(get("/provento/tipo/Dividendos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].dataPagamento").value("2025-01-01"))
                .andExpect(jsonPath("$[0].tipoProvento").value("Dividendos"))
                .andExpect(jsonPath("$[0].codigo").value("ACAO4"))
                .andExpect(jsonPath("$[0].quantidade").value("10"))
                .andExpect(jsonPath("$[0].valorTotal").value("10"))
                .andExpect(jsonPath("$[0].valorMedio").value("1.0"))
                .andReturn();
    }

    @Test
    void buscarPeloIdErro() throws Exception {
        when(proventoService.buscarPeloId(1L)).thenThrow(new NotFoundException("Provento", 1L));

        mockMvc.perform(get("/provento/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.erro").value("NAO_ENCONTRADO"))
                .andExpect(jsonPath("$.mensagem").value("Provento #1 não encontrado"))
                .andExpect(jsonPath("$.status").value("404"));
    }

    @Test
    void remover() throws Exception {
        mockMvc.perform(delete("/provento/1"))
                .andExpect(status().isOk());
    }
}