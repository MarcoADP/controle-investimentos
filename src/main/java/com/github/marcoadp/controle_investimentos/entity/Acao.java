package com.github.marcoadp.controle_investimentos.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Setter
    @Column
    private String nome;

    @Setter
    @Column
    private String codigo;

    @Setter
    @Column
    private String cnpj;

    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "setor_id")
    private Setor setor;

}
