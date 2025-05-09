package com.github.marcoadp.controle_investimentos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity(name = "acao_br")
@Builder
public class Acao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "acao_id")
    private Long id;

    @Column
    private String nome;

    @Column
    private String codigo;

    @Column
    private String cnpj;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "setor_id")
    private Setor setor;

}
