package com.github.marcoadp.controle_investimentos.controller;

import com.github.marcoadp.controle_investimentos.handler.GlobalExceptionHandler;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.mapper.FundoImobiliarioMapper;
import com.github.marcoadp.controle_investimentos.service.FundoImobiliarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static com.github.marcoadp.controle_investimentos.stub.FundoImobiliarioStub.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FundoImobiliarioController.class)
@Import(GlobalExceptionHandler.class)
class FundoImobiliarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FundoImobiliarioService fundoImobiliarioService;

    @MockitoBean
    private FundoImobiliarioMapper fundoImobiliarioMapper;

    @Test
    void criar() throws Exception {

        var fundoImobiliario = getFundoImobiliario();
        when(fundoImobiliarioService.criar(getFundoImobiliarioRequest())).thenReturn(fundoImobiliario);
        when(fundoImobiliarioMapper.toFundoImobiliarioResponse(fundoImobiliario)).thenReturn(getFundoImobiliarioResponse());

        mockMvc.perform(post("/fundo-imobiliario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(FI_REQUEST_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("FI"));

    }

    @Test
    void atualizar() throws Exception {
        var fundoImobiliario = getFundoImobiliario();
        when(fundoImobiliarioService.atualizar(1L, getFundoImobiliarioRequest())).thenReturn(fundoImobiliario);
        when(fundoImobiliarioMapper.toFundoImobiliarioResponse(fundoImobiliario)).thenReturn(getFundoImobiliarioResponse());

        mockMvc.perform(put("/fundo-imobiliario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(FI_REQUEST_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("FI"));
    }

    @Test
    void buscar() throws Exception {
        var fundoImobiliario = getFundoImobiliario();
        when(fundoImobiliarioService.buscar("FIMO11", "Tipo", null)).thenReturn(List.of(fundoImobiliario));
        when(fundoImobiliarioMapper.toFundoImobiliarioResponse(fundoImobiliario)).thenReturn(getFundoImobiliarioResponse());

        mockMvc.perform(get("/fundo-imobiliario?codigo=FIMO11&tipo=Tipo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nome").value("FI"))
                .andExpect(jsonPath("$[0].tipo").value("Tipo"))
                .andExpect(jsonPath("$[0].segmento").value("Segmento"))
                .andExpect(jsonPath("$[0].cnpj").value("26492737000162"))
                .andExpect(jsonPath("$[0].codigo").value("FIMO11"));
    }

    @Test
    void buscarPeloId() throws Exception {
        var fundoImobiliario = getFundoImobiliario();
        when(fundoImobiliarioService.buscarPeloId(1L)).thenReturn(fundoImobiliario);
        when(fundoImobiliarioMapper.toFundoImobiliarioResponse(fundoImobiliario)).thenReturn(getFundoImobiliarioResponse());

        mockMvc.perform(get("/fundo-imobiliario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("FI"));
    }

    @Test
    void buscarPeloCodigo() throws Exception {
        var fundoImobiliario = getFundoImobiliario();
        when(fundoImobiliarioService.buscarPeloCodigo(fundoImobiliario.getCodigo())).thenReturn(Optional.of(fundoImobiliario));
        when(fundoImobiliarioMapper.toFundoImobiliarioResponse(fundoImobiliario)).thenReturn(getFundoImobiliarioResponse());

        mockMvc.perform(get("/fundo-imobiliario/codigo/FIMO11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("FI"))
                .andExpect(jsonPath("$.tipo").value("Tipo"))
                .andExpect(jsonPath("$.segmento").value("Segmento"))
                .andExpect(jsonPath("$.cnpj").value("26492737000162"))
                .andExpect(jsonPath("$.codigo").value("FIMO11"));
    }

    @Test
    void buscarPeloIdErro() throws Exception {
        when(fundoImobiliarioService.buscarPeloId(1L)).thenThrow(new NotFoundException("Fundo Imobiliário", 1L));

        mockMvc.perform(get("/fundo-imobiliario/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.erro").value("NAO_ENCONTRADO"))
                .andExpect(jsonPath("$.mensagem").value("Fundo Imobiliário #1 não encontrado"))
                .andExpect(jsonPath("$.status").value("404"));
    }

    @Test
    void remover() throws Exception {
        mockMvc.perform(delete("/fundo-imobiliario/1"))
                .andExpect(status().isOk());
    }
}