package lt.mif.ise.error.mapping;

import lt.mif.ise.error.Error;
import lt.mif.ise.error.exception.BadRequestException;
import lt.mif.ise.error.exception.NotFoundException;
import lt.mif.ise.error.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandlingController {
    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Error> handleRuntime(RuntimeException ex) {
        return CreateError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<Error> handleNotFound(RuntimeException ex) {
        return CreateError(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    protected ResponseEntity<Error> handleBadRequest(RuntimeException ex) {
        return CreateError(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    protected ResponseEntity<Error> handleUnauthorized(RuntimeException ex) {
        return CreateError(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<Error> CreateError (String message, HttpStatus statusCode){
        Error error = new Error();
        error.Message = message;
        return new ResponseEntity<Error>(error, statusCode);
    }
}
