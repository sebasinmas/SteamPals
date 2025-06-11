package com.steampals.steampals.model;


import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Biblioteca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToMany
    private Set<Juego> juegos = new HashSet<>();

    //Numero de juegos debería ser removido puesto que el atributo size de juegos contiene la cantidad de juegos
    /*private int numeroDeJuegos;*/

    //Si consideramos la cantidad de logros, no deberíamos extraer la info de los logros de cada juego, 
    //por lo que habría que crear otra clase, llamada LOGRO, y que tenga distintos atributos?
    private int cantidadDeLogros;

    public void agregarJuego(Juego juego){
        if(!juegos.contains(juego)){
            juegos.add(juego);
        }
    }
        public void eliminarJuego(Juego juego) {
        if (juegos.contains(juego)) {
            juegos.remove(juego);
        }
    }
    public int getNumeroDeJuegos(){
        return this.getJuegos().size();
    }
}
