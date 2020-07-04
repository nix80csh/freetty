package com.freetty.exception.test;

public class FreettyLogicException extends RuntimeException {

	private static final long serialVersionUID = 1L;
    private ErrorList errorCode;
    
    public static FreettyLogicException wrap(Throwable exception, ErrorList errorCode) {
        if (exception instanceof FreettyLogicException) {
        	FreettyLogicException se = (FreettyLogicException)exception;
        	if (errorCode != null && errorCode != se.getErrorCode()) {
                return new FreettyLogicException(exception.getMessage(), exception, errorCode);
			}
			return se;
        } else {
            return new FreettyLogicException(exception.getMessage(), exception, errorCode);
        }
    }
    
    public static FreettyLogicException wrap(Throwable exception) {
    	return wrap(exception, null);
    }
    
    public FreettyLogicException(ErrorList errorCode) {
		this.errorCode = errorCode;
	}

	public FreettyLogicException(String message, ErrorList errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public FreettyLogicException(Throwable cause, ErrorList errorCode) {
		super(cause);
		this.errorCode = errorCode;
	}

	public FreettyLogicException(String message, Throwable cause, ErrorList errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}
	
	public ErrorList getErrorCode() {
        return errorCode;
    }
	
	public FreettyLogicException setErrorCode(ErrorList errorCode) {
        this.errorCode = errorCode;
        return this;
    }

}
