package com.reece.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RestException {
    public EntityNotFoundException(String recordType, Long id) {
        super("Could not find " + recordType + " with id='" + id + "'.");
    }
}
