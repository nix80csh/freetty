/**
 * 
 */
package com.freetty.dto;

import lombok.Data;

/**
 * @Project : freetty-api-customer
 * @FileName : OrderDto.java
 * @Date : 2017. 3. 24.
 * @작성자 : 조성훈
 * @설명 : 주문과 관련된 데이터 전송 객체
 **/

public class OrderDto {

  @Data
  public static class ReadAddrDto {
    private Integer idfCustomer;
    private String addr1;
    private String addr2;
    private Double latitude;
    private Double longitude;
    private String isAvailableDistance;
  }

}
