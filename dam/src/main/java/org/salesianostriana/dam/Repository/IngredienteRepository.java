package org.salesianostriana.dam.Repository;

import org.salesianostriana.dam.modelos.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {

    boolean existsByNombre(String nombre);
}
