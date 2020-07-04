package com.freetty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freetty.common.GoogleMapsApiUtil;
import com.freetty.dto.JsonDto;
import com.freetty.dto.OrderDto.ReadAddrDto;
import com.freetty.entity.Customer;
import com.freetty.repository.CustomerRepo;
import com.google.maps.model.LatLng;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
@Transactional
public class OrderServcieImpl {

  @Autowired CustomerRepo customerRepo;
  @Autowired GoogleMapsApiUtil googleMapsApiUtil;

  @Value("${imp.key}") private String impKey;
  @Value("${imp.secret}") private String impSecret;

  public JsonDto<?> registOrderResult(String idfImport, String idfPg) {
    JsonDto<String> jDto = new JsonDto<String>();

    if (getImpAccessToken() != null) {
      if (getOrderResult(idfImport, getImpAccessToken()) == 0) {
        jDto.setResultCode("S");
      } else {
        jDto.setResultCode("F");
        jDto.setResultMessage("Invalid imp_uid");
      }

    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("Invalid accessToken");
    }
    return jDto;
  }

  private Integer getOrderResult(String idfImport, String accessToken) {
    Integer resultCode;
    String pgTid;
    String buyerTel;
    Integer amount;
    HttpResponse<JsonNode> response;
    String authUrl = "https://api.iamport.kr/payments/" + idfImport;
    try {
      response = Unirest.get(authUrl).header("Accept", "application/json")
          .header("X-ImpTokenHeader", accessToken).asJson();
      resultCode = (Integer) response.getBody().getObject().get("code");
      pgTid = (String) response.getBody().getObject().getJSONObject("response").get("pg_tid");
      buyerTel = (String) response.getBody().getObject().getJSONObject("response").get("buyer_tel");
      amount = (Integer) response.getBody().getObject().getJSONObject("response").get("amount");
      System.out.println(pgTid);
      System.out.println(buyerTel);
      System.out.println(amount);

    } catch (UnirestException e) {
      resultCode = null;
    }
    return resultCode;
  }

  private String getImpAccessToken() {
    Integer resultCode;
    String accessToken = null;
    HttpResponse<JsonNode> response;
    String authUrl = "https://api.iamport.kr/users/getToken";
    try {
      response = Unirest.post(authUrl).header("Accept", "application/json").field("imp_key", impKey)
          .field("imp_secret", impSecret).asJson();
      resultCode = (Integer) response.getBody().getObject().get("code");
      if (resultCode == 0) {
        accessToken =
            (String) response.getBody().getObject().getJSONObject("response").get("access_token");
      }
    } catch (UnirestException e) {
      accessToken = null;
    }
    return accessToken;
  }

  /**
   * @작성일 : 2017. 3. 24.
   * @설명 :
   * 
   *     <pre>
   *     고객 식별자와 현재 위치의 위도,경도로 
   *     기본주소를 조회하고 조회된 주소로     
   *     출장이 가능한지 계산하여 알려준다
   *     </pre>
   **/
  public JsonDto<?> readAddrByIdfCustomer(ReadAddrDto readAddrDto) {
    JsonDto<ReadAddrDto> jDto = new JsonDto<ReadAddrDto>();

    if (customerRepo.exists(readAddrDto.getIdfCustomer())) {
      Customer customer = customerRepo.findOne(readAddrDto.getIdfCustomer());
      if (customer.getAddr1() != null) {

        LatLng latLng = googleMapsApiUtil.getLatLng(customer.getAddr1());

        Double longitude = latLng.lng;
        Double latitude = latLng.lat;

      } else {
        jDto.setResultCode("F");
        jDto.setResultMessage("does not exist customer addr");
      }

    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist customer");
    }

    return jDto;
  }

}
