package com.freetty.service;

import java.util.HashMap;
import java.util.Random;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.freetty.common.SmsUtil;
import com.freetty.config.ApplicationConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationConfig.class)
@WebAppConfiguration
public class SmsServiceTest {

	@Autowired
	SmsUtil smsUtil;
	
	@Before
	public void setup() {

	}

	@Test
	public void smsSendTest() {
		Random random = new Random();
		int smsCode = random.nextInt(10000) + 1000;
		if (smsCode > 10000) {
			smsCode = smsCode - 1000;
		}
		String SMSMessage = "Freetty 인증서비스 입니다.  [" + Integer.toString(smsCode) + "] 인증번호를 입력하세요.";

		HashMap<String, String> set = new HashMap<String, String>();
		set.put("to", "01049209123"); // 수신번호
		set.put("text", SMSMessage); // 문자내용
		set.put("type", "sms"); // 문자 타입

		JSONObject result = smsUtil.send(set); // 보내기&전송결과받기
		
		if ((Boolean) result.get("status") == true) {
			// 메시지 보내기 성공 및 전송결과 출력
			System.out.println(result.get("group_id").toString());
			// jDto.setResultMessage("");
		} else {
			System.out.println(result.get("message").toString());
		}
		
	}

}
