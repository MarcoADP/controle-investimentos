package com.github.marcoadp.controle_investimentos.entity;

import com.github.marcoadp.controle_investimentos.entity.converter.TipoAtivoEnumConverter;
import com.github.marcoadp.controle_investimentos.enums.TipoAtivoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ConsolidacaoProvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consolidacao_provento_id")
    private Long id;

    @Column
    private String codigo;

    @Setter
    @Column(name = "tipo_ativo")
    @Convert(converter = TipoAtivoEnumConverter.class)
    private TipoAtivoEnum tipoAtivo;

    private Integer ano;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "valor_medio")
    private BigDecimal valorMedio;

}
