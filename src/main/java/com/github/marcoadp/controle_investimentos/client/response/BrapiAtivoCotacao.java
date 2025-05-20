package com.github.marcoadp.controle_investimentos.client.response;

import java.math.BigDecimal;

public record BrapiAtivoCotacao(String Currency,
                                String shortName,
                                String longName,
                                BigDecimal regularMarketChange,
                                BigDecimal regularMarketChangePercent,
                                String regularMarketTime,
                                BigDecimal regularMarketPrice,
                                BigDecimal regularMarketDayHigh,
                                String regularMarketDayRange,
                                BigDecimal regularMarketDayLow,
                                Integer regularMarketVolume,
                                BigDecimal regularMarketPreviousClose,
                                BigDecimal regularMarketOpen,
                                String fiftyTwoWeekRange,
                                BigDecimal fiftyTwoWeekLow,
                                BigDecimal fiftyTwoWeekHigh,
                                String symbol,
                                BigDecimal priceEarnings,
                                BigDecimal earningsPerShare,
                                String logourl
) {
}
