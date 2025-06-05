package com.steampals.steampals.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que representa un usuario en la base de datos.
 * Contiene los atributos necesarios para el registro y autenticación de un usuario.
 * Se utiliza la anotación @Entity para indicar que esta clase es una entidad JPA.
 * Se utiliza la anotación @Builder para permitir la creación de objetos de esta clase utilizando el patrón Builder.
 * Se utilizan las anotaciones @Getter y @Setter para generar automáticamente los métodos getter y setter para cada atributo.
 * Se utilizan las anotaciones @AllArgsConstructor y @NoArgsConstructor para generar constructores con todos los atributos y sin atributos, respectivamente.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Usuario {
    /**
     * ID único del usuario, generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true,nullable=false)
    private Long id;
    /**
     * Nombre de usuario del usuario, es un nickname no único
     */
    private String usuario;
    /**
     * Correo electrónico del usuario, es único y se utiliza para la autenticación.
     */
    private String email;
    /**
     * Contraseña del usuario, se almacena de forma segura en la base de datos.
     */
    private String contrasenia;
    /**
     * Nombre real del usuario, se utiliza para mostrar el nombre del usuario en la aplicación.
     */
    private int edad;

    /**
     * País del usuario, se utiliza para mostrar la ubicación del usuario en la aplicación.
     */
    private String pais;
    
    /**
     * Rol del usuario, se utiliza para determinar los permisos del usuario en la aplicación.
     */
    private String rol;


    /**
     * Descripción del usuario, se utiliza para mostrar información adicional sobre el usuario en la aplicación.
     */
    private String descripcion;
}
