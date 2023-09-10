package com.exception;


import com.common.Errors;
import com.model.dto.DefaultErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class InvalidCredentialsException extends RuntimeException implements AppErrorResponse {
    Errors code;

    @Override
    public DefaultErrorResponse getDefaultError() {
        return new DefaultErrorResponse(code);
    }
}