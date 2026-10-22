package org.salesianostriana.dam.Repository;

import org.salesianostriana.dam.modelos.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetaRepository extends JpaRepository<Receta, Long> {

    boolean existsByNombre(String nombre);
}
