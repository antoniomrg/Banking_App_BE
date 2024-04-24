package ex8_CC_SpringBoot.demo.Exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({NoResourceFoundException.class})

    public ResponseEntity<?> handleNoResourceFoundException(NoResourceFoundException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body("Not found");
    }



//    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
//        return ResponseEntity.internalServerError().body("User not found");
//    }
}
