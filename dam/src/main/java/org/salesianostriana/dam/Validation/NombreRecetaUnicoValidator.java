package org.salesianostriana.dam.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.salesianostriana.dam.Repository.RecetaRepository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@RequiredArgsConstructor
public class NombreRecetaUnicoValidator implements ConstraintValidator<NombreRecetaUnico, String> {

    private final RecetaRepository repository;

    @Override
    public boolean isValid(String nombre, ConstraintValidatorContext context) {
        // Si viene vacío, de eso se encarga @NotBlank
        if (nombre == null || nombre.isBlank()) {
            return true;
        }

        Long idEnRuta = obtenerIdDeLaRuta();

        if (idEnRuta == null) {
            return !repository.existsByNombre(nombre);
        }
        return !repository.existsByNombreAndIdNot(nombre, idEnRuta);
    }

    @SuppressWarnings("unchecked")
    private Long obtenerIdDeLaRuta() {
        if (!(RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attrs)) {
            return null;
        }

        Map<String, String> variables = (Map<String, String>) attrs.getRequest()
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if (variables == null || !variables.containsKey("id")) {
            return null;
        }

        try {
            return Long.valueOf(variables.get("id"));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
