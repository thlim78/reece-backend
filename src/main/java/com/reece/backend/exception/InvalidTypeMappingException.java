package com.reece.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTypeMappingException extends RestException {
    public InvalidTypeMappingException(String type) {
        super("The values provided for " + type + " is invalid.");
    }
}
