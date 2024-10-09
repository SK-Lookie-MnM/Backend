package com.mnm.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class QnaNotFoundException extends RuntimeException {
    public QnaNotFoundException(String message) {
        super(message);
    }
}
