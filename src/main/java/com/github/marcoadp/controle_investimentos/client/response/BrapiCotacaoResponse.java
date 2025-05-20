package com.github.marcoadp.controle_investimentos.client.response;

import java.util.List;

public record BrapiCotacaoResponse(List<BrapiAtivoCotacao> results, String requestedAt, String took) {
}
