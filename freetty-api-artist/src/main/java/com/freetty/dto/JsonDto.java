package com.freetty.dto;

import java.util.List;

import lombok.Data;


@Data
public class JsonDto<entityType> {	
	private String resultCode;
	private String resultMessage;
	private entityType dataObject;
	private List<entityType> dataList;

}