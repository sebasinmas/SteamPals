package com.steampals.steampals.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que representa un usuario en la base de datos.
 * Contiene los atributos necesarios para el registro y autenticación de un
 * usuario.
 * Se utiliza la anotación @Entity para indicar que esta clase es una entidad
 * JPA.
 * Se utiliza la anotación @Builder para permitir la creación de objetos de esta
 * clase utilizando el patrón Builder.
 * Se utilizan las anotaciones @Getter y @Setter para generar automáticamente
 * los métodos getter y setter para cada atributo.
 * Se utilizan las anotaciones @AllArgsConstructor y @NoArgsConstructor para
 * generar constructores con todos los atributos y sin atributos,
 * respectivamente.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "usuario")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {
    /**
     * ID único del usuario, generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * ID de Steam del usuario, utilizado para vincular la cuenta de Steam
     */

    private String steamId;

    /**
     * Nombre de usuario del usuario, es un nickname no único
     */
    private String nombreUsuario;
    /**
     * Correo electrónico del usuario, es único y se utiliza para la autenticación.
     */
    private String email;
    /**
     * Contraseña del usuario, se almacena de forma segura en la base de datos.
     */
    private String contrasenia;
    /**
     * Nombre real del usuario, se utiliza para mostrar el nombre del usuario en la
     * aplicación.
     */
    private int edad;

    /**
     * País del usuario, se utiliza para mostrar la ubicación del usuario en la
     * aplicación.
     */
    private String pais;

    /**
     * Rol del usuario, se utiliza para determinar los permisos del usuario en la
     * aplicación.
     */
    @Enumerated(EnumType.STRING)
    private Rol rol;

    /**
     * Descripción del usuario, se utiliza para mostrar información adicional sobre
     * el usuario en la aplicación.
     */
    private String descripcion;

    /**
     * Lista de juegos favoritos del usuario, se utiliza para mostrar los juegos
     * favoritos del usuario en la aplicación.
     */
    private LocalDate fechaDeRegistro;

    /**
     * Indica si el usuario está suspendido, se utiliza para bloquear el acceso del
     * usuario a la aplicación.
     */
    private Boolean suspendido;

    @OneToMany(mappedBy = "usuario1")
    private Set<MatchUsuario> matchesComoUsuario1;

    @OneToMany(mappedBy = "usuario2")
    private Set<MatchUsuario> matchesComoUsuario2;

    @Builder.Default
    @OneToMany(mappedBy = "usuario")
    private Set<UsuarioTieneJuego> usuarioTieneJuego = new HashSet<>();
    @Builder.Default
    @OneToMany(mappedBy = "emisor")
    private List<MensajePrivado> mensajesPrivEnviados = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "receptor")
    private List<MensajePrivado> mensajesPrivRecibidos = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "emisor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MensajeGrupal> mensajesGrupalesEnviados = new ArrayList<>();
    
    @Builder.Default
    @OneToMany(mappedBy = "reportado")
    private List<Reporte> reportesRecibidos = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "reportador")
    private List<Reporte> reportesRealizados = new ArrayList<>();



    /**
     * Enumeración que representa los roles posibles de un usuario en la aplicación.
     * USUARIO: Rol por defecto, con permisos básicos.
     * ADMINISTRADOR: Rol con permisos avanzados para gestionar la aplicación.
     */
    public enum Rol {
        USUARIO, ADMINISTRADOR
    }
}
