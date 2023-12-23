package com.jonas.PaymentAccounts.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionsDetails> bussinessexceptionHandler(BusinessException exception){
        var errors = new HashMap<String,String>();
        errors.put("error",exception.getMessage());

        ExceptionsDetails exceptionsDetails = new ExceptionsDetails(
                "Business Exception",
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                exception.getClass().getSimpleName(),
                errors);

        return new ResponseEntity<>(exceptionsDetails,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ExceptionsDetails> sqlExceptionHandler(SQLException exception){
        var errors = new HashMap<String,String>();

        SQLException currentException = exception;
        int count = 1;
        while (currentException != null) {
            errors.put("SQL Exception: " + count,currentException.getMessage());
            currentException = currentException.getNextException();
        }

        ExceptionsDetails exceptionsDetails = new ExceptionsDetails(
                "SQL Exception",
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                exception.getClass().getSimpleName(),
                errors);

        return new ResponseEntity<>(exceptionsDetails,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionsDetails> sqlExceptionHandler(MethodArgumentNotValidException exception){
        var errors = new HashMap<String,String>();

        exception.getBindingResult()
                .getAllErrors()
                .stream()
                .forEach((error)-> {

                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName,errorMessage);
                });

        ExceptionsDetails exceptionsDetails = new ExceptionsDetails(
                "Validation Exception",
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                exception.getClass().getSimpleName(),
                errors);

        return new ResponseEntity<>(exceptionsDetails,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionsDetails> entityNotFoundExceptionHandler(EntityNotFoundException exception){
        var errors = new HashMap<String,String>();
        errors.put("error",exception.getMessage());

        ExceptionsDetails exceptionsDetails = new ExceptionsDetails(
                "Not Found Exception",
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                exception.getClass().getSimpleName(),
                errors);

        return new ResponseEntity<>(exceptionsDetails,HttpStatus.BAD_REQUEST);
    }


}
