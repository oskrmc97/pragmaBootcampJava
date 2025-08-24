package enums;

/**
 * En esta clase se especifican los diferentes tipos de errores que se pueden presentar a la hora de
 * prestar un servicio a un cliente, Aqui se agrega la TICO_0008 con fines didacticos.
 * @author
 *
 */
public enum TICOEnum {
    TICO_9999("Se ha presentado un error tecnico", "TICO_9999"),
    TICO_0001("El idComic %s debe ser mayor a 0", "TICO_0001"),
    TICO_0002("La longitud máxima permitida es de 50 caracteres", "TICO_0002"),
    TICO_0005("El nombre del comic no debe ser mayor a 50 caracteres", "TICO_0005"),
    TICO_0008("El Comic NO existe en la base de datos", "TICO_0008"),
    TICO_5555("La cantidad existente del comic es: %s, y supera la ingresada", "TICO_5555"),
    TICO_0000("El comic seleccionado no cuenta con stock en bodega", "TICO_0000")
    ;

    private String message;
    private String code;

    TICOEnum(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}

