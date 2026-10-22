package error;

public class CategoriaNoEncontradaException extends RuntimeException {

    public CategoriaNoEncontradaException(Long id) {
        super("No se ha encontrado la categoría con id " + id);
    }
}
