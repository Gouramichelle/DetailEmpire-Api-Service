package com.detailempire.service.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nombre: "Lavado Full"
    @Column(nullable = false)
    private String name;

    // precio en pesos (por ejemplo 20000)
    @Column(nullable = false)
    private Integer price;

    // descripción opcional
    @Column(length = 1000)
    private String description;

    // foto opcional (URL, path, etc.)
    private String photoUrl;

    // para que el admin pueda activar/desactivar el servicio
    @Column(nullable = false)
    private Boolean active;

    @PrePersist
    public void prePersist() {
        if (active == null) {
            active = true;
        }
    }
}
