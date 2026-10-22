package modelos;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    private String descripcion;

    @OneToMany(mappedBy = "categoria")
    @Builder.Default
    private List<Receta> recetas = new ArrayList<>();

    // Métodos helper para mantener sincronizados los dos lados de la relación
    public void addReceta(Receta receta) {
        recetas.add(receta);
        receta.setCategoria(this);
    }

    public void removeReceta(Receta receta) {
        recetas.remove(receta);
        receta.setCategoria(null);
    }
}
