package com.freetty.exception.test;

import lombok.Data;

@Data
public class InternalServerExceptionVo {

	private int errorCode;
	private String errorMsg;
}
