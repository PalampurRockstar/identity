package com.common;

import com.exception.InvalidAccessTokenException;
import com.exception.InvalidCredentialsException;
import com.model.dto.DefaultErrorResponse;
import lombok.var;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value= { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific";
        return handleExceptionInternal(ex, bodyOfResponse,new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
    @ExceptionHandler(InvalidAccessTokenException.class)
    public ResponseEntity<Object> handleInvalidAccessTokenException(InvalidAccessTokenException ex, WebRequest request) {
        var code=ex.getCode();
        return handleExceptionInternal(ex,new DefaultErrorResponse(code),new HttpHeaders(), code.getHttp(), request);
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> handleCredentialsException(InvalidCredentialsException ex, WebRequest request) {
        var code=ex.getCode();
        return handleExceptionInternal(ex,new DefaultErrorResponse(code),new HttpHeaders(), code.getHttp(), request);
    }

}