package com.github.marcoadp.controle_investimentos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Setor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "setor_id")
    private Long id;

    @Setter
    @Column
    private String nome;

    public Setor(String nome) {
        this.nome = nome;
    }
}
