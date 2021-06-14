package com.reece.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestNotValidException extends RestException {
    public RequestNotValidException(String reason, Exception exception) {
        super("Invalid request: '" + reason + "'.", exception);
    }

    public RequestNotValidException(String reason) {
        super("Invalid request: '" + reason + "'.");
    }
}
