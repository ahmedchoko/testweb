package com.wevioo.pi.exception;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BadRequestResponse {
    private String path;
    private Map<String,List<String>> errors;
    private String message;
    private String fieldName;

    public BadRequestResponse(String path, Map<String, List<String>> errors, String message) {
        this.path=path;
        this.errors=errors;
        this.message=message;
    }
    
    public BadRequestResponse(String path,String fieldName ,String message) {
        this.path=path;
        this.fieldName=fieldName;
        this.message=message;
    }

}
