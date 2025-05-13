package com.github.marcoadp.controle_investimentos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity(name = "brazilian_depositary_receipts")
@Builder
public class Bdr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bdr_id")
    private Long id;

    @Column
    private String nome;

    @Column
    private String codigo;

    @Column
    private String cnpj;

    @Column
    private String setor;

}
