package com.exception;


import com.common.Errors;
import com.model.dto.DefaultErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class InvalidAccessTokenException extends RuntimeException implements AppErrorResponse {

    private Errors code;
    @Override
    public DefaultErrorResponse getDefaultError() {
        return new DefaultErrorResponse(code);
    }
}