package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.dto.response.AtivoResponse;
import com.github.marcoadp.controle_investimentos.enums.TipoAtivoEnum;
import com.github.marcoadp.controle_investimentos.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtivoServiceImpl implements AtivoService {

    private final AcaoService acaoService;

    private final BdrService bdrService;

    private final EtfService etfService;

    private final FundoImobiliarioService fundoImobiliarioService;

    @Override
    public AtivoResponse buscarAtivo(String codigo) {
        var acaoOpt = acaoService.buscarPeloCodigo(codigo);
        if (acaoOpt.isPresent()) {
            return new AtivoResponse(codigo, TipoAtivoEnum.ACAO.getDescricao(), acaoOpt.get().getSetor().getNome());
        }
        var bdrOpt = bdrService.buscarPeloCodigo(codigo);
        if (bdrOpt.isPresent()) {
            return new AtivoResponse(codigo, TipoAtivoEnum.BDR.getDescricao(), bdrOpt.get().getSetor());
        }
        var etfOpt = etfService.buscarPeloCodigo(codigo);
        if (etfOpt.isPresent()) {
            return new AtivoResponse(codigo, TipoAtivoEnum.ETF.getDescricao(), etfOpt.get().getTipo());
        }
        var fiOpt = fundoImobiliarioService.buscarPeloCodigo(codigo);
        if (fiOpt.isPresent()) {
            return new AtivoResponse(codigo, TipoAtivoEnum.FI.getDescricao(), fiOpt.get().getCodigo());
        }

        return new AtivoResponse(codigo, "NAO_ENCONTRADO", "NAO_ENCONTRADO");
    }
}
