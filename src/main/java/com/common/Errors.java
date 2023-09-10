package com.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum Errors {
    P001("001", "Invalid credentials", HttpStatus.UNAUTHORIZED),
    P002("002", "Invalid Token",HttpStatus.FORBIDDEN);

    private final String code;
    private final String message;
    private final HttpStatus http;
}
