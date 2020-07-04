package com.freetty.exception.test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InternalServerExceptionHandler {
	
	@ExceptionHandler(FreettyLogicException.class)
	public ResponseEntity<InternalServerExceptionVo> internalServerException(FreettyLogicException ex){
		InternalServerExceptionVo vo = new InternalServerExceptionVo();
		vo.setErrorCode(ex.getErrorCode().getErrorCode());
		vo.setErrorMsg(ex.getErrorCode().getErrorMsg());
		return new ResponseEntity<InternalServerExceptionVo>(vo, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}