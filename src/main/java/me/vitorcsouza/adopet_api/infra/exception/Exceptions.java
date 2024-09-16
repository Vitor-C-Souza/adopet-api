package me.vitorcsouza.adopet_api.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class Exceptions {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> Error(Exception exception) {
        ResponseError responseError = new ResponseError(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
        return ResponseEntity.badRequest().body(responseError);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseError> noSuchElementException( NoSuchElementException exception){
        ResponseError responseError = new ResponseError(
                "Não encontrado.",
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();

        ResponseError responseError = new ResponseError(
                errorMessage,
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );

        return ResponseEntity.badRequest().body(responseError);
    }
}
