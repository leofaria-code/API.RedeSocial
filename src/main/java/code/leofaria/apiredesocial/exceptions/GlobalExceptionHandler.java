package code.leofaria.apiredesocial.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> entityNotFoundException(EntityNotFoundException exception, WebRequest request){
        ErrorResponse errorDetails =
                new ErrorResponse(new Date(),
                        HttpStatus.NOT_FOUND.toString(),
                        exception.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<?> entityAlreadyExistsException(EntityNotFoundException exception, WebRequest request){
        ErrorResponse errorDetails =
                new ErrorResponse(new Date(),
                        HttpStatus.UNPROCESSABLE_ENTITY.toString(),
                        exception.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception exception, WebRequest request) {
        ErrorResponse errorDetails =
                new ErrorResponse(new Date(),
                        HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                        exception.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
