package com.freetty.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freetty.common.BeanUtil;
import com.freetty.common.SmsUtil;
import com.freetty.dto.ArtistCustomerDto.ArtistCustomerManagerDto;
import com.freetty.dto.CustomerDto;
import com.freetty.dto.CustomerDto.SearchCustomerDto;
import com.freetty.dto.CustomerDto.sendInviteSMS;
import com.freetty.dto.JsonDto;
import com.freetty.entity.Artist;
import com.freetty.entity.ArtistCustomer;
import com.freetty.entity.ArtistCustomerPK;
import com.freetty.entity.Customer;
import com.freetty.repository.ArtistCustomerRepo;
import com.freetty.repository.ArtistRepo;
import com.freetty.repository.CustomerRepo;

@Service
@Transactional
public class CustomerServiceImpl {

  @Autowired
  CustomerRepo customerRepo;
  @Autowired
  ArtistRepo artistRepo;
  @Autowired
  ArtistCustomerRepo artistCustomerRepo;

  @Autowired
  SmsUtil smsUtil;

  public JsonDto<?> searchCustomerByMobile(Integer idfArtist, String mobile) {
    JsonDto<SearchCustomerDto> jDto = new JsonDto<SearchCustomerDto>();
    SearchCustomerDto searchCustomerDto = new SearchCustomerDto();
    if (customerRepo.findByMobile(mobile) != null) {
      Customer customer = customerRepo.findByMobile(mobile);
      BeanUtil.copyProperties(customer, searchCustomerDto);

      // "추가됨", "추가하기" 값
      ArtistCustomerPK id = new ArtistCustomerPK();
      id.setIdfArtist(idfArtist);
      id.setIdfCustomer(customer.getIdfCustomer());
      if (artistCustomerRepo.exists(id)) {
        searchCustomerDto.setIsAdded("Y");
      } else {
        searchCustomerDto.setIsAdded("N");
      }

      jDto.setResultCode("S");
      jDto.setDataObject(searchCustomerDto);
    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist customer");
    }
    return jDto;
  }

  public JsonDto<?> sendInvite(sendInviteSMS sendInviteSMSDto) {
    JsonDto<sendInviteSMS> jDto = new JsonDto<sendInviteSMS>();

    Artist artist = artistRepo.findOne(sendInviteSMSDto.getIdfArtist());
    if (artist.getMobile() != null) {

      String SMSMessage = artist.getName() + "님이 " + sendInviteSMSDto.getToName()
          + "고객님을 FREETTY로 초대하였습니다! 이제, FREETTY에서 " + artist.getName()
          + "님의 포트폴리오를 바로 확인하고 예약해 보세요! 아름다움을 만드는, 당신의 이름이 브랜드가 되는곳, FREETTY를 지금 바로 다운로드 받으세요!";

      HashMap<String, String> set = new HashMap<String, String>();
      set.put("to", sendInviteSMSDto.getMobile()); // 수신번호
      set.put("text", SMSMessage); // 문자내용
      set.put("type", "lms"); // 문자 타입

      JSONObject result = smsUtil.send(set); // 보내기&전송결과받기
      if ((Boolean) result.get("status") == true) {
        jDto.setResultCode("S");
      } else {
        jDto.setResultCode("F");
        jDto.setResultMessage(result.get("code").toString());
      }
    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist mobile number");
    }

    return jDto;
  }

  public JsonDto<?> readAllByIdfArtist(Integer idfArtist) {
    JsonDto<ArtistCustomerManagerDto> jDto = new JsonDto<ArtistCustomerManagerDto>();

    ArtistCustomerManagerDto artistCustomerManagerDto = new ArtistCustomerManagerDto();
    artistCustomerManagerDto
        .setCountArtistCustomer(artistCustomerRepo.countByIdIdfArtist(idfArtist));

    List<ArtistCustomer> artistCustomerList = artistCustomerRepo.findByIdIdfArtist(idfArtist);
    List<CustomerDto> customerDtoList = new ArrayList<CustomerDto>();
    for (ArtistCustomer artistCustomer : artistCustomerList) {
      CustomerDto customerDto = new CustomerDto();
      customerDto.setName(artistCustomer.getCustomer().getName());
      customerDto.setNickname(artistCustomer.getCustomer().getNickname());
      customerDtoList.add(customerDto);
    }
    artistCustomerManagerDto.setCustomerList(customerDtoList);
    jDto.setResultCode("S");
    jDto.setDataObject(artistCustomerManagerDto);

    return jDto;
  }

  public JsonDto<?> checkMobileValidation(Integer idfArtist) {
    JsonDto<Map<String, String>> jDto = new JsonDto<Map<String, String>>();
    Map<String, String> resultMap = new HashMap<String, String>();
    if (artistRepo.exists(idfArtist)) {
      Artist artist = artistRepo.findOne(idfArtist);
      if (artist.getMobile() != null) {
        resultMap.put("name", artist.getName());
        jDto.setResultCode("S");
        jDto.setDataObject(resultMap);

      } else {
        jDto.setResultCode("F");
        jDto.setResultMessage("fail to mobile validation");
      }
    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist artist");
    }

    return jDto;
  }

}
