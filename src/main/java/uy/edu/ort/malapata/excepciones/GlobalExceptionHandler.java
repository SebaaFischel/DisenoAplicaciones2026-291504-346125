package uy.edu.ort.malapata.excepciones;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final int errorCodeStatus = 299;

    @ExceptionHandler(MalaPataException.class)
    public ResponseEntity<String> manejarException(MalaPataException ex) {
        return ResponseEntity.status(errorCodeStatus).body(ex.getMessage());
    }
}