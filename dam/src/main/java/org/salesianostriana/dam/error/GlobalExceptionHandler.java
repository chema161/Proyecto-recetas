package org.salesianostriana.dam.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.jspecify.annotations.Nullable;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String BASE_TYPE = "https://recetas.example.com/errors/";

    // 404 - Recurso no encontrado
    @ExceptionHandler({
            CategoriaNoEncontradaException.class,
            RecetaNoEncontradaException.class,
            IngredienteNoEncontradaException.class
    })
    public ProblemDetail handleNoEncontrado(RuntimeException ex) {
        return crearProblema(HttpStatus.NOT_FOUND,
                "Recurso no encontrado", ex.getMessage(), "recurso-no-encontrado");
    }

    // 409 - El ingrediente ya está en la receta
    @ExceptionHandler(IngredienteYaAnadidoException.class)
    public ProblemDetail handleIngredienteYaAnadido(IngredienteYaAnadidoException ex) {
        return crearProblema(HttpStatus.CONFLICT,
                "Ingrediente ya añadido", ex.getMessage(), "ingrediente-ya-anadido");
    }

    // 400 - Tiempo de preparación menor o igual a cero
    @ExceptionHandler(TiempoInvalidoException.class)
    public ProblemDetail handleTiempoInvalido(TiempoInvalidoException ex) {
        return crearProblema(HttpStatus.BAD_REQUEST,
                "Tiempo de preparación inválido", ex.getMessage(), "tiempo-invalido");
    }

    // 409 - Violación de integridad (nombre duplicado, borrar algo que está en uso, etc)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleIntegridad(DataIntegrityViolationException ex) {
        return crearProblema(HttpStatus.CONFLICT,
                "Conflicto de integridad de datos",
                "La operación viola una restricción de datos: nombre duplicado o recurso en uso",
                "conflicto-integridad");
    }

    // 500 - Cualquier error no controlado
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneral(Exception ex) {
        return crearProblema(HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno del servidor", "Se ha producido un error inesperado", "error-interno");
    }

    // 400 - Errores de validación de @Valid, con la lista de campos inválidos
    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, "Error de validación");
        problem.setTitle("Datos de entrada inválidos");
        problem.setType(URI.create(BASE_TYPE + "validacion"));

        List<ApiValidationSubError> subErrors = ex.getAllErrors().stream()
                .map(ApiValidationSubError::from)
                .toList();
        problem.setProperty("invalid-params", subErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    private ProblemDetail crearProblema(HttpStatus status, String titulo, String detalle, String tipo) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detalle);
        problem.setTitle(titulo);
        problem.setType(URI.create(BASE_TYPE + tipo));
        return problem;
    }

    @Builder
    record ApiValidationSubError(
            String object,
            String message,
            @JsonInclude(JsonInclude.Include.NON_NULL)
            String field,
            @JsonInclude(JsonInclude.Include.NON_NULL)
            Object rejectedValue
    ) {

        public static ApiValidationSubError from(ObjectError error) {
            if (error instanceof FieldError fieldError) {
                return ApiValidationSubError.builder()
                        .object(fieldError.getObjectName())
                        .message(fieldError.getDefaultMessage())
                        .field(fieldError.getField())
                        .rejectedValue(fieldError.getRejectedValue())
                        .build();
            }
            return ApiValidationSubError.builder()
                    .object(error.getObjectName())
                    .message(error.getDefaultMessage())
                    .build();
        }
    }
}
