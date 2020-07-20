package com.io.github.lucasgabrielgit.clientes.rest;

import com.io.github.lucasgabrielgit.clientes.rest.exception.APIErrors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

//API que trata as exceções e retorna a resposta como lista de erros
@RestControllerAdvice
public class ApplicationControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIErrors handleValidationErrors(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> messages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return new APIErrors(messages);
    }

    
    //Trata as exceções e retorna uma lista de erros junto com o Código de Status das ações tomadas no servidor
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity handleResponseStatusException(ResponseStatusException ex) {
        String msgErro = ex.getMessage();
        HttpStatus statusCode = ex.getStatus();
        APIErrors errorsAPI = new APIErrors(msgErro);
        return new ResponseEntity(errorsAPI, statusCode);
    }
}
