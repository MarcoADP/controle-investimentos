package com.github.marcoadp.controle_investimentos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carteira_id")
    private Long id;

    @Column
    private String nome;

    @Column
    private String descricao;

    @Setter
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "carteira_id")
    private List<CarteiraAtivo> ativos;
}
