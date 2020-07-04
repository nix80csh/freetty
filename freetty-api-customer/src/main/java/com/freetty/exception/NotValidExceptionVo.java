package com.freetty.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

public class NotValidExceptionVo {
	
	private final int status;
    private final String message;
    private List<FieldError> fieldErrors = new ArrayList<>();

    NotValidExceptionVo(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
    
    public void addFieldError(String field, String message) {
        FieldError error = new FieldError(field, message);
        fieldErrors.add(error);
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
    
    @Data
    public static class FieldError{
    	private String field;
    	private String message;    	
    	FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}
