package lt.mif.ise.error.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException (String message){
        super(message);
    }
}
