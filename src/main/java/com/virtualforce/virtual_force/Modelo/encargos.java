package com.virtualforce.virtual_force.Modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "encargos")
public class encargos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column(name = "id_encargo")
    private Long id_encargo;
    @Getter @Setter @Column(name = "encargo")
    private String encargo;
    @Getter @Setter @Column(name = "fecha_encargo")
    private String fecha_encargo;
    @Getter @Setter @Column(name = "pago")
    private String pago;
    @Getter @Setter @Column(name = "comentario")
    private String comentario;
    @Getter @Setter @Column(name = "respuesta")
    private String respuesta;
    @Getter @Setter @Column(name = "img")
    private String img;
    @Getter @Setter @Column(name = "nombre_imagen")
    private String nombre_imagen;
    @Getter @Setter @Column(name = "tipo_imagen")
    private String tipo_imagen;
    @Getter @Setter @Column(name = "enviar_img")
    private String enviar_img;
}
