package error;

public class TiempoInvalidoException extends RuntimeException {

    public TiempoInvalidoException(Integer tiempo) {
        super("El tiempo de preparación debe ser mayor que 0 (valor recibido: " + tiempo + ")");
    }
}