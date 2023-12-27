package com.virtualforce.virtual_force.Modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "atendido")
public class Atendido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "id_atendido")
    private Long id_atendido;
    @Getter @Setter @Column(name = "id_encargo")
    private Long id_encargo;
    @Getter @Setter @Column(name = "id_usuario")
    private Long id_usuario;
    @Getter @Setter @Column(name = "fecha_encargo")
    private String fecha_encargo;
    @Getter @Setter @Column(name = "nombre")
    private String nombre;
    @Getter @Setter @Column(name = "apellido")
    private String apellido;
    @Getter @Setter @Column(name = "telefono")
    private String telefono;
    @Getter @Setter @Column(name = "email")
    private String email;
    @Getter @Setter @Column(name = "contrasenia")
    private String contrasenia;
}
