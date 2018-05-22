package lt.mif.ise.error.mapping;

import lt.mif.ise.error.Error;
import lt.mif.ise.error.exception.BadRequestException;
import lt.mif.ise.error.exception.NotFoundException;
import lt.mif.ise.error.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.OptimisticLockException;

@ControllerAdvice
public class GlobalExceptionHandlingController {
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

    @ExceptionHandler(value = {OptimisticLockException.class})
    protected ResponseEntity<Error> handleOptimisticLocking(RuntimeException ex) {
        return CreateError(ex.getMessage(), HttpStatus.CONFLICT);
    }

    private ResponseEntity<Error> CreateError (String message, HttpStatus statusCode){
        Error error = new Error();
        error.Message = message;
        return new ResponseEntity<Error>(error, statusCode);
    }
}
