package com.io.github.lucasgabrielgit.clientes.rest.exception;

import lombok.Getter;

import java.util.*;

public class APIErrors {
    @Getter
    private List<String> errors;

    public APIErrors(List<String> errors) {
        this.errors = errors;
    }

    public APIErrors(String message) {
        this.errors = Arrays.asList(message);
    }
}
