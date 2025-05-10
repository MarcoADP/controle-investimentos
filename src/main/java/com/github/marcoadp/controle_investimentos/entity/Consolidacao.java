package com.github.marcoadp.controle_investimentos.entity;

import com.github.marcoadp.controle_investimentos.entity.converter.TipoAtivoEnumConverter;
import com.github.marcoadp.controle_investimentos.enums.TipoAtivoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Consolidacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consolidacao_id")
    private Long id;

    @Column
    private String codigo;

    @Setter
    @Column(name = "tipo_ativo")
    @Convert(converter = TipoAtivoEnumConverter.class)
    private TipoAtivoEnum tipoAtivo;

    @Column(name = "quantidade_entrada")
    private BigDecimal quantidadeEntrada;

    @Column(name = "valor_medio_entrada")
    private BigDecimal valorMedioEntrada;

    @Column(name = "valor_total_entrada")
    private BigDecimal valorTotalEntrada;

    @Column(name = "quantidade_saida")
    private BigDecimal quantidadeSaida;

    @Column(name = "valor_medio_saida")
    private BigDecimal valorMedioSaida;

    @Column(name = "valor_total_saida")
    private BigDecimal valorTotalSaida;

    @Column(name = "lucro_medio")
    private BigDecimal lucroMedio;

    @Column(name = "lucro_proporcao")
    private BigDecimal lucroProporcao;

    public void calcularEntrada(BigDecimal quantidadeEntrada, BigDecimal valorTotalEntrada) {
        this.quantidadeEntrada = quantidadeEntrada;
        this.valorTotalEntrada = valorTotalEntrada;
        this.valorMedioEntrada = valorTotalEntrada.divide(quantidadeEntrada, 2, RoundingMode.HALF_UP);
    }

    public void calcularSaida(BigDecimal quantidadeSaida, BigDecimal valorTotalSaida) {
        this.quantidadeSaida = quantidadeSaida;
        this.valorTotalSaida = valorTotalSaida;
        this.valorMedioSaida = Objects.equals(quantidadeSaida, BigDecimal.ZERO)
                ? BigDecimal.ZERO : valorTotalSaida.divide(quantidadeSaida, 2, RoundingMode.HALF_UP);
    }

    public void calcularLucro() {
        this.lucroMedio = Objects.equals(quantidadeSaida, BigDecimal.ZERO)
                ? BigDecimal.ZERO : this.valorMedioSaida.subtract(this.valorMedioEntrada);
        this.lucroProporcao = this.lucroMedio.divide(this.valorMedioEntrada, 5, RoundingMode.HALF_UP);
    }

}
