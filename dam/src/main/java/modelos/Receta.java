package modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "recetas")
public class Receta {

    @Id
    long id;
    String nombre;
    int tiempoPreparacionMin;

    @Enumerated(EnumType.STRING)
    private DificultadEnum dificultad;


}
