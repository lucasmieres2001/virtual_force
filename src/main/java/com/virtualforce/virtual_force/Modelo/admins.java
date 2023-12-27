package com.virtualforce.virtual_force.Modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "admins",uniqueConstraints = @UniqueConstraint(columnNames = "usuario"))

public class admins {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column(name = "id_admins")
    private Long id_admins;
    @Getter @Setter @Column(name = "usuario",unique = true)
    private String usuario;
    @Getter @Setter @Column(name = "contrasenia")
    private String contrasenia;
}
