package com.model.dto;



import lombok.Data;
import com.common.*;


@Data
public class DefaultErrorResponse {
    public DefaultErrorResponse(Errors code){
        this.code=code.getCode();
        this.message=code.getMessage();
    }
    private String code;
    private String message;
}
