package com.freetty.service;

import java.util.HashMap;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freetty.common.EmailValidMailboxLayerUtil;
import com.freetty.common.EncryptionSha256Util;
import com.freetty.common.SmsUtil;
import com.freetty.common.TokenUtil;
import com.freetty.dto.AuthDto;
import com.freetty.dto.AuthDto.ModifyPasswordDto;
import com.freetty.dto.AuthDto.ValidateSMSCodeDto;
import com.freetty.dto.JsonDto;
import com.freetty.entity.Artist;
import com.freetty.repository.ArtistRepo;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
@Transactional(readOnly = true)
public class AuthServiceImpl {

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	TokenUtil tokenUtil;
	@Autowired
	MailSender mailSender;
	@Autowired
	ArtistRepo artistRepo;
	@Autowired
	SmsUtil smsUtil;
	@Autowired
	EmailValidMailboxLayerUtil emailValidMailboxLayerUtil;

	@Value("${freetty.token.header}")
	private String tokenHeader;

	public JsonDto<?> signin(AuthDto authDto, HttpServletResponse res, Device device) {
		JsonDto<AuthDto> jDto = new JsonDto<AuthDto>();

		try {
			// 인증
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authDto.getEmail(),
					authDto.getPassword());
			Authentication authentication = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// 토큰생성
			Artist artist = artistRepo.findByEmail(authDto.getEmail());
			String xAuthToken = tokenUtil.createToken(artist.getEmail(), artist.getPassword());

			// 쿠키생성
			Cookie cookie = new Cookie(this.tokenHeader, xAuthToken);
			res.addCookie(cookie);

			authDto.setIdfArtist(artist.getIdfArtist());
			authDto.setPassword(null);

			jDto.setResultCode("S");
			jDto.setDataObject(authDto);

		} catch (BadCredentialsException e) {
			jDto.setResultCode("F");
			jDto.setResultMessage("Invalid Email or Password");
		}
		return jDto;
	}

	public JsonDto<?> signup(AuthDto authDto) {
		JsonDto<AuthDto> jDto = new JsonDto<AuthDto>();
		if (artistRepo.findByEmail(authDto.getEmail()) == null) {
			if (emailValidMailboxLayerUtil.checkValid(authDto.getEmail())) {
				Artist artist = new Artist();
				artist.setEmail(authDto.getEmail());
				artist.setPassword(authDto.getPassword());
				artist.setReservationPolicy("A");
				artistRepo.save(artist);
				jDto.setResultCode("S");
			} else {
				jDto.setResultCode("F");
				jDto.setResultMessage("invalid email");
			}
		} else {
			jDto.setResultCode("F");
			jDto.setResultMessage("exist artist");
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

	public JsonDto<?> modifyPassword(ModifyPasswordDto modifyPasswordDto) {
		JsonDto<ModifyPasswordDto> jDto = new JsonDto<ModifyPasswordDto>();

		Artist artist = artistRepo.findOne(modifyPasswordDto.getIdfArtist());
		if (artist.getPassword().equals(modifyPasswordDto.getOldPassword())) {
			artist.setPassword(modifyPasswordDto.getNewPassword());
			artistRepo.save(artist);
			jDto.setResultCode("S");
			jDto.setResultMessage("changed password");
		} else {
			jDto.setResultCode("F");
			jDto.setResultMessage("invalid password");
		}
		return jDto;
	}
	// "Could not commit JPA transaction; nested exception is
	// javax.persistence.RollbackException:
	// Transaction marked as rollbackOnly"
	// @Transactional(rollbackFor = EmptyResultDataAccessException.class)
	// public JsonDto<?> removeArtist(Integer idfArtist) {
	// JsonDto<String> jDto = new JsonDto<String>();
	// try {
	// artistRepo.delete(idfArtist);
	// jDto.setResultCode("S");
	// jDto.setResultMessage("removed artist account");
	// } catch (EmptyResultDataAccessException e) {
	// jDto.setResultCode("F");
	// jDto.setResultMessage("does not exist artist");
	// }
	// return jDto;
	// }

	@Transactional
	public JsonDto<?> removeArtist(Integer idfArtist) {
		JsonDto<String> jDto = new JsonDto<String>();
		if (artistRepo.exists(idfArtist)) {
			artistRepo.delete(idfArtist);
			jDto.setResultCode("S");
			jDto.setResultMessage("removed artist account");
		} else {
			jDto.setResultCode("F");
			jDto.setResultMessage("does not exist artist");
		}
		return jDto;
	}

	@Transactional
	public JsonDto<?> sendPasswordToEmail(String email) {
		JsonDto<String> jDto = new JsonDto<String>();

		if (artistRepo.findByEmail(email) != null) {
			String tempPassword = getTempPassword();
			Artist artist = artistRepo.findByEmail(email);
			String encPassword = EncryptionSha256Util.getEncSHA256(tempPassword);
			artist.setPassword(encPassword);
			artistRepo.save(artist);

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
				buf.append((char) ((int) (rnd.nextInt(26)) + 65));
			} else {
				buf.append((rnd.nextInt(10)));
			}
		}
		return buf.toString();
	}

}
