package com.freetty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.freetty.dto.JsonDto;
import com.freetty.dto.OrderDto.ReadAddrDto;
import com.freetty.service.OrderServcieImpl;

@Controller
@RequestMapping("/order")
public class OrderController {

  @Autowired OrderServcieImpl OrderService;

  @RequestMapping(value = "/registOrderResult", method = RequestMethod.GET)
  public ResponseEntity<?> registOrderResult(@RequestParam("imp_uid") String idfImport,
      @RequestParam("merchant_uid") String idfPg) {
    JsonDto<?> jDto = OrderService.registOrderResult(idfImport, idfPg);
    return new ResponseEntity<>(jDto, HttpStatus.OK);
  }

  @RequestMapping(value = "/readAddrByIdfCustomer", method = RequestMethod.POST)
  public ResponseEntity<?> readAddrByIdfCustomer(@RequestBody ReadAddrDto readAddrDto) {
    JsonDto<?> jDto = OrderService.readAddrByIdfCustomer(readAddrDto);
    return new ResponseEntity<>(jDto, HttpStatus.OK);
  }

}
