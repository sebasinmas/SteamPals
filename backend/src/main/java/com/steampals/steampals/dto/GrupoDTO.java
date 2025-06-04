package com.steampals.steampals.dto;
import com.steampals.steampals.model.MensajeGrupal;
import com.steampals.steampals.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrupoDTO {
    private Long id;
    private String nombre;
    private String fechaCreacion;
    private String descripcion;
    private List<Usuario> miembros;
    private List<MensajeGrupal> mensajes;
}