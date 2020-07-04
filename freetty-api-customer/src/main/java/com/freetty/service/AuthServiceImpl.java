package com.freetty.service;

import java.util.HashMap;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.freetty.common.EmailValidMailboxLayerUtil;
import com.freetty.common.EncryptionSha256Util;
import com.freetty.common.SmsUtil;
import com.freetty.common.TokenUtil;
import com.freetty.dto.AuthDto;
import com.freetty.dto.AuthDto.ModifyPasswordDto;
import com.freetty.dto.AuthDto.ValidateSMSCodeDto;
import com.freetty.dto.JsonDto;
import com.freetty.entity.Customer;
import com.freetty.repository.CustomerRepo;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
@Transactional
public class AuthServiceImpl {

  @Autowired CustomerRepo customerRepo;
  @Autowired TokenUtil tokenUtil;
  @Autowired EmailValidMailboxLayerUtil emailValidMailboxLayerUtil;
  @Autowired AuthenticationManager authenticationManager;
  @Autowired MailSender mailSender;

  @Autowired SmsUtil smsUtil;
  @Value("${freetty.token.header}") private String tokenHeader;

  public JsonDto<AuthDto> ftSignup(AuthDto authDto, HttpServletResponse res) {
    JsonDto<AuthDto> jDto = new JsonDto<AuthDto>();

    if (customerRepo.findByEmail(authDto.getEmail()) == null) {
      if (emailValidMailboxLayerUtil.checkValid(authDto.getEmail())) {
        Customer customer = new Customer();
        customer.setEmail(authDto.getEmail());
        customer.setPassword(authDto.getPassword());
        customer.setPlatform("FT");
        customerRepo.save(customer);

        jDto.setResultCode("S");

      } else {
        jDto.setResultCode("F");
        jDto.setResultMessage("invalid email");
      }

    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("exist customer");
    }

    return jDto;
  }

  public JsonDto<AuthDto> ftSignin(AuthDto authDto, HttpServletResponse res) {
    JsonDto<AuthDto> jDto = new JsonDto<AuthDto>();
    try {
      // 인증
      UsernamePasswordAuthenticationToken token =
          new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword());
      Authentication authentication = authenticationManager.authenticate(token);
      SecurityContextHolder.getContext().setAuthentication(authentication);

      // 토큰생성
      Customer customer = customerRepo.findByEmail(authDto.getEmail());
      String xAuthToken = tokenUtil.createToken(customer.getEmail(), customer.getPassword());

      // 쿠키생성
      Cookie cookie = new Cookie(this.tokenHeader, xAuthToken);
      res.addCookie(cookie);

      authDto.setIdfCustomer(customer.getIdfCustomer());
      authDto.setPassword(null);

      jDto.setResultCode("S");
      jDto.setDataObject(authDto);
    } catch (BadCredentialsException e) {
      jDto.setResultCode("F");
      jDto.setResultMessage("Invalid Email or Password");
    }
    return jDto;
  }

  public JsonDto<AuthDto> fbSignin(String accessToken, HttpServletResponse res) {
    JsonDto<AuthDto> jDto = new JsonDto<AuthDto>();
    HttpResponse<JsonNode> response;
    try {
      response = Unirest
          .get("https://graph.facebook.com/v2.8/me?fields=email,id&access_token=" + accessToken)
          .header("Accept", "application/json").asJson();
      String email = (String) response.getBody().getObject().get("email");
      String userId = (String) response.getBody().getObject().get("id");

      if (customerRepo.countByEmail(email) == 0) {
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(EncryptionSha256Util.getEncSHA256(userId));
        customer.setPlatform("FB");
        customerRepo.save(customer);
      } else {
        // 인증
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email,
            EncryptionSha256Util.getEncSHA256(userId));
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("인증완료");
      }

      // 토큰생성
      String xAuthToken = tokenUtil.createToken(email, EncryptionSha256Util.getEncSHA256(userId));

      // 쿠키생성
      Cookie cookie = new Cookie(this.tokenHeader, xAuthToken);
      res.addCookie(cookie);

      Customer customer = customerRepo.findByEmail(email);
      AuthDto authDto = new AuthDto();
      authDto.setIdfCustomer(customer.getIdfCustomer());
      authDto.setEmail(customer.getEmail());
      authDto.setPassword(null);
      jDto.setResultCode("S");
      jDto.setDataObject(authDto);

    } catch (UnirestException e1) {
      jDto.setResultCode("F");
      jDto.setResultMessage("invalid accessToken");
    } catch (JSONException e2) {
      jDto.setResultCode("F");
      jDto.setResultMessage("invalid accessToken");
    } catch (BadCredentialsException e) {
      jDto.setResultCode("F");
      jDto.setResultMessage("Invalid Email or Password");
    }

    return jDto;
  }

  public JsonDto<?> sendSMSCode(ValidateSMSCodeDto validateSMSCodeDto) {
    JsonDto<ValidateSMSCodeDto> jDto = new JsonDto<ValidateSMSCodeDto>();
    Random random = new Random();
    int smsCode = random.nextInt(10000) + 1000;
    if (smsCode > 10000) {
      smsCode = smsCode - 1000;
    }
    String SMSMessage = "Freetty 인증서비스 입니다.  [" + Integer.toString(smsCode) + "] 인증번호를 입력하세요.";

    HashMap<String, String> set = new HashMap<String, String>();
    set.put("to", validateSMSCodeDto.getMobile()); // 수신번호
    set.put("text", SMSMessage); // 문자내용
    set.put("type", "sms"); // 문자 타입

    JSONObject result = smsUtil.send(set); // 보내기&전송결과받기
    if ((Boolean) result.get("status") == true) {
      // 메시지 보내기 성공 및 전송결과 출력
      validateSMSCodeDto.setMessageId(result.get("group_id").toString());
      jDto.setResultCode("S");
      jDto.setDataObject(validateSMSCodeDto);
    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage(result.get("code").toString());
    }
    return jDto;
  }

  public JsonDto<?> validateSMSCode(ValidateSMSCodeDto validateSMSCodeDto) {
    JsonDto<ValidateSMSCodeDto> jDto = new JsonDto<ValidateSMSCodeDto>();

    HashMap<String, String> set = new HashMap<String, String>();
    set.put("gid", validateSMSCodeDto.getMessageId()); // group_id
    set.put("s_rcpt", validateSMSCodeDto.getMobile()); // 수신번호

    JSONObject result = smsUtil.sent(set);
    if ((Boolean) result.get("status") == true) {
      JSONArray data = (JSONArray) result.get("data");
      JSONObject obj = (JSONObject) data.get(0);
      String SMSCode = obj.get("text").toString();
      System.out.println("조회 : " + SMSCode.substring(21, 25));

      if (SMSCode.substring(21, 25).equals(validateSMSCodeDto.getSmsCode())) {
        jDto.setResultCode("S");
      } else {
        jDto.setResultCode("F");
        jDto.setResultMessage("invalid SMSCode");
      }
    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("sms system connection failed");
    }
    return jDto;
  }

  @Transactional
  public JsonDto<?> sendPasswordToEmail(String email) {
    JsonDto<String> jDto = new JsonDto<String>();

    if (customerRepo.findByEmail(email) != null) {
      String tempPassword = getTempPassword();
      Customer customer = customerRepo.findByEmail(email);
      String encPassword = EncryptionSha256Util.getEncSHA256(tempPassword);
      customer.setPassword(encPassword);
      customerRepo.save(customer);

      SimpleMailMessage msg = new SimpleMailMessage();
      msg.setSubject("Freetty 임시비밀번호 발송");
      msg.setText("임시비밀번호 : " + tempPassword);
      msg.setTo(email);
      msg.setFrom("dev@freetty.com");
      try {
        mailSender.send(msg);
        jDto.setResultCode("S");
      } catch (Exception e) {
        jDto.setResultCode("F");
        jDto.setResultMessage(e.getMessage());
      }
    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist email");
    }
    return jDto;
  }

  private String getTempPassword() {
    Random rnd = new Random();
    StringBuffer buf = new StringBuffer();
    // 10자리 영문숫자 조합 임시 비빌번호 생성
    for (int i = 0; i < 10; i++) {
      if (rnd.nextBoolean()) {
        buf.append((char) ((rnd.nextInt(26)) + 65));
      } else {
        buf.append((rnd.nextInt(10)));
      }
    }
    return buf.toString();
  }

  public JsonDto<?> modifyPassword(ModifyPasswordDto modifyPasswordDto) {
    JsonDto<ModifyPasswordDto> jDto = new JsonDto<ModifyPasswordDto>();

    Customer customer = customerRepo.findOne(modifyPasswordDto.getIdfCustomer());
    if (customer != null) {
      if (customer.getPassword().equals(modifyPasswordDto.getOldPassword())) {
        customer.setPassword(modifyPasswordDto.getNewPassword());
        customerRepo.save(customer);
        jDto.setResultCode("S");
        jDto.setResultMessage("changed password");
      } else {
        jDto.setResultCode("F");
        jDto.setResultMessage("invalid password");
      }
    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist customer");
    }
    return jDto;
  }

  @Transactional
  public JsonDto<?> removeCustomer(Integer idfCustomer) {
    JsonDto<String> jDto = new JsonDto<String>();
    if (customerRepo.exists(idfCustomer)) {
      customerRepo.delete(idfCustomer);
      jDto.setResultCode("S");
      jDto.setResultMessage("removed customer account");
    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist customer");
    }
    return jDto;
  }

}
