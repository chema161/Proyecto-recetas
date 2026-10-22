package org.salesianostriana.dam.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NombreRecetaUnicoValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NombreRecetaUnico {

    String message() default "{receta.nombre.unico}";

    Class<?>[] groups() default {};

    Class<?  extends Payload>[] payload() default {};
}
