package com.factoriaF5.cukies.exception;

import com.factoriaF5.cukies.DTOs.ErrorDTO;
import com.factoriaF5.cukies.exception.category.*;
import com.factoriaF5.cukies.exception.customer.*;
import com.factoriaF5.cukies.exception.product.*;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //maneja excepciones NotFound
    @ExceptionHandler({
            ProductNotFoundException.class,
            ProductsNotFoundException.class,
            CategoryNotFoundException.class,
            CategoriesNotFoundException.class,
            CustomerNotFoundException.class,
            CustomersNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(ApiException e) {
        String errorCode = "GENERAL_ERROR";

        if (e instanceof ProductNotFoundException) {
            errorCode = "PRODUCT_NOT_FOUND";
        } else if (e instanceof ProductsNotFoundException) {
            errorCode = "PRODUCTS_NOT_FOUND";
        } else if (e instanceof CategoryNotFoundException) {
            errorCode = "CATEGORY_NOT_FOUND";
        } else if (e instanceof CategoriesNotFoundException) {
            errorCode = "CATEGORIES_NOT_FOUND";
        } else if (e instanceof CustomerNotFoundException) {
            errorCode = "CUSTOMER_NOT_FOUND";
        } else if (e instanceof CustomersNotFoundException) {
            errorCode = "CUSTOMERS_NOT_FOUND";
        }

        List<ErrorDTO> errorDTOList = List.of(new ErrorDTO(errorCode, e.getMessage()));
        ErrorResponse errorResponse = new ErrorResponse(errorDTOList, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    //maneja excepciones
    @ExceptionHandler(CustomerExistsException.class)
    public ResponseEntity<ErrorResponse> handleCustomerExists(CustomerExistsException e) {
        List<ErrorDTO> errorDTOList = List.of(new ErrorDTO("CUSTOMER_EXISTS", "Ja existeix un client amb les dades proporcionades."));
        ErrorResponse errorResponse = new ErrorResponse(errorDTOList, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

     //maneja excepciones de validación
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        List<ErrorDTO> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> new ErrorDTO("VALIDATION_ERROR", error.getDefaultMessage()))
                .toList();

        return new ResponseEntity<>(new ErrorResponse(errors, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    //maneja excepciones genéricas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        List<ErrorDTO> errorDTOList = List.of(
                new ErrorDTO("INTERNAL_SERVER_ERROR", "An unexpected error occurred: " + e.getMessage())
        );
        ErrorResponse errorResponse = new ErrorResponse(errorDTOList, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //maneja excepciones si hay problemas con la conexión de la base de datos
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseException(DataAccessException e) {
        List<ErrorDTO> errorDTOList = List.of(
                new ErrorDTO("DATABASE_ERROR", "Database error: "+ e.getMessage())
        );
        ErrorResponse errorResponse = new ErrorResponse(errorDTOList, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
