package com.wevioo.pi.auth.exception;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BadRequestResponse {
    private String path;
    private Map<String, List<String>> errors;
    private String message;

    public BadRequestResponse(String path, Map<String, List<String>> errors, String message) {
        this.path=path;
        this.errors=errors;
        this.message=message;
    }
}
