package com.freetty.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.freetty.dto.JsonDto;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class NotValidExceptionHandler {

	@ResponseStatus(BAD_REQUEST)
	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public JsonDto<NotValidExceptionVo> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		return processFieldErrors(fieldErrors);
	}

	private JsonDto<NotValidExceptionVo> processFieldErrors(List<FieldError> fieldErrors) {
		NotValidExceptionVo badRequestVo = new NotValidExceptionVo(BAD_REQUEST.value(), "validation error");
		JsonDto<NotValidExceptionVo> jDto = new JsonDto<NotValidExceptionVo>();
		for (FieldError fieldError : fieldErrors) {
			badRequestVo.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		jDto.setResultCode("F");
		jDto.setResultMessage(badRequestVo.getFieldErrors().get(0).getField() + " " + badRequestVo.getMessage());
		// jDto.setDataObject(badRequestVo);
		return jDto;
	}
}