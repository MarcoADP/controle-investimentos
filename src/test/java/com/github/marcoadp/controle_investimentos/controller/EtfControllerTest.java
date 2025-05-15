package com.github.marcoadp.controle_investimentos.controller;

import com.github.marcoadp.controle_investimentos.handler.GlobalExceptionHandler;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.mapper.EtfMapper;
import com.github.marcoadp.controle_investimentos.service.EtfService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static com.github.marcoadp.controle_investimentos.stub.EtfStub.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EtfController.class)
@Import(GlobalExceptionHandler.class)
class EtfControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EtfService etfService;

    @MockitoBean
    private EtfMapper etfMapper;

    @Test
    void criar() throws Exception {

        var etf = getEtf(1L);
        when(etfService.criar(getEtfRequest())).thenReturn(etf);
        when(etfMapper.toEtfResponse(etf)).thenReturn(getEtfResponse());

        mockMvc.perform(post("/etf")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ETF_REQUEST_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("ETF"));

    }

    @Test
    void atualizar() throws Exception {
        var etf = getEtf(1L);
        when(etfService.atualizar(1L, getEtfRequest())).thenReturn(etf);
        when(etfMapper.toEtfResponse(etf)).thenReturn(getEtfResponse());

        mockMvc.perform(put("/etf/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ETF_REQUEST_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("ETF"));
    }

    @Test
    void listar() throws Exception {
        var etf = getEtf(1L);
        when(etfService.listar()).thenReturn(List.of(etf));
        when(etfMapper.toEtfResponse(etf)).thenReturn(getEtfResponse());

        mockMvc.perform(get("/etf"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nome").value("ETF"))
                .andExpect(jsonPath("$[0].tipo").value("Tipo"))
                .andExpect(jsonPath("$[0].cnpj").value("26492737000162"))
                .andExpect(jsonPath("$[0].codigo").value("ETF11"));
    }

    @Test
    void buscarPeloId() throws Exception {
        var etf = getEtf(1L);
        when(etfService.buscarPeloId(1L)).thenReturn(etf);
        when(etfMapper.toEtfResponse(etf)).thenReturn(getEtfResponse());

        mockMvc.perform(get("/etf/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("ETF"));
    }

    @Test
    void buscarPeloCodigo() throws Exception {
        var etf = getEtf(1L);
        when(etfService.buscarPeloCodigo(etf.getCodigo())).thenReturn(Optional.of(etf));
        when(etfMapper.toEtfResponse(etf)).thenReturn(getEtfResponse());

        mockMvc.perform(get("/etf/codigo/ETF11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("ETF"))
                .andExpect(jsonPath("$.tipo").value("Tipo"))
                .andExpect(jsonPath("$.cnpj").value("26492737000162"))
                .andExpect(jsonPath("$.codigo").value("ETF11"));
    }

    @Test
    void buscarPeloTipo() throws Exception {
        var etf = getEtf(1L);
        when(etfService.buscarPeloTipo("Tipo")).thenReturn(List.of(etf));
        when(etfMapper.toEtfResponse(etf)).thenReturn(getEtfResponse());

        mockMvc.perform(get("/etf/tipo/Tipo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nome").value("ETF"))
                .andExpect(jsonPath("$[0].tipo").value("Tipo"))
                .andExpect(jsonPath("$[0].cnpj").value("26492737000162"))
                .andExpect(jsonPath("$[0].codigo").value("ETF11"));
    }

    @Test
    void buscarPeloIdErro() throws Exception {
        when(etfService.buscarPeloId(1L)).thenThrow(new NotFoundException("Etf", 1L));

        mockMvc.perform(get("/etf/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.erro").value("NAO_ENCONTRADO"))
                .andExpect(jsonPath("$.mensagem").value("Etf #1 não encontrado"))
                .andExpect(jsonPath("$.status").value("404"));
    }

    @Test
    void remover() throws Exception {
        mockMvc.perform(delete("/etf/1"))
                .andExpect(status().isOk());
    }
}