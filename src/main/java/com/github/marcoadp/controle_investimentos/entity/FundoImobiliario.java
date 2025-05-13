package com.github.marcoadp.controle_investimentos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity(name = "fundo_imobiliario")
@Builder
public class FundoImobiliario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fundo_imobiliario_id")
    private Long id;

    @Column
    private String nome;

    @Column
    private String codigo;

    @Column
    private String cnpj;

    @Column
    private String tipo;

    @Column
    private String segmento;

}
