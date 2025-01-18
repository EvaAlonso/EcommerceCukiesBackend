package com.factoriaF5.cukies.exception.customer;

import com.factoriaF5.cukies.exception.ApiException;
import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends ApiException {
    private final String searchField;
    private final String searchValue;

    //si no se encuentra por username o email
    public CustomerNotFoundException(String searchField, String searchValue) {
        super("User with " + searchField + " " + searchValue + " not found.", HttpStatus.NOT_FOUND);
        this.searchField = searchField;
        this.searchValue = searchValue;
    }

    //si no se encuentra por id
    public CustomerNotFoundException(String searchField, int searchValue) {
        super("User with " + searchField + " " + searchValue + " not found.", HttpStatus.NOT_FOUND);
        this.searchField = searchField;
        this.searchValue = String.valueOf(searchValue);
    }
}

