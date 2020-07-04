/**
 * 
 */
package com.freetty.exception.test;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
  * @Project : freetty-api-customer
  * @FileName : DuplicateEntityExceptionMsg.java  
  * @Date : 2017. 3. 17.
  * @작성자 : 조성훈
  * @설명 : 중복 엔티티 타입의 메시지 추상화
**/

@Getter
@AllArgsConstructor
public enum ErrorList {
	
	DuplicateEntity_Artist(101,"DuplicateEntity.Artist"),
	DuplicateEntity_Customer(102, "DuplicateEntity.Customer");

	private final int errorCode;
	private final String errorMsg;
	
}
