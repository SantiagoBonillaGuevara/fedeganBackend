package com.example.fedegan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vacunaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vacunacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Asumo que id_vacunador hace referencia a Usuario.id
    @JsonIgnore
    private Long idVacunador;

    private String finca;

    private String campaign;

    private LocalDateTime fecha;

    private String tipoVacuna;

    private String observaciones;

    @Column(name = "creado_en", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime creadoEn;
}

