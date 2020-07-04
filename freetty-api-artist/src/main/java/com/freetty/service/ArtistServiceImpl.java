package com.freetty.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freetty.common.BeanUtil;
import com.freetty.common.CloudinaryUtil;
import com.freetty.common.ImageUtil;
import com.freetty.dto.ArtistCustomerDto.RegistArtistCustomerDto;
import com.freetty.dto.ArtistDto;
import com.freetty.dto.ArtistDto.AddArtistCustomerDto;
import com.freetty.dto.ArtistDto.ModifyReservationPolicy;
import com.freetty.dto.ArtistDto.SaveProfileImageDto;
import com.freetty.dto.ArtistScheduleDto;
import com.freetty.dto.ArtistShopDto;
import com.freetty.dto.JsonDto;
import com.freetty.entity.Artist;
import com.freetty.entity.ArtistCustomer;
import com.freetty.entity.ArtistCustomerPK;
import com.freetty.entity.ArtistSchedule;
import com.freetty.entity.ArtistShop;
import com.freetty.repository.ArtistCustomerRepo;
import com.freetty.repository.ArtistLicenseRepo;
import com.freetty.repository.ArtistRepo;
import com.freetty.repository.ArtistScheduleRepo;
import com.freetty.repository.ArtistShopRepo;

@Service
@Transactional
public class ArtistServiceImpl {

	@Autowired
	ArtistRepo artistRepo;
	@Autowired
	ArtistShopRepo artistShopRepo;
	@Autowired
	ArtistScheduleRepo artistScheduleRepo;
	@Autowired
	ArtistCustomerRepo artistCustomerRepo;
	@Autowired
	ArtistLicenseRepo artistLicenseRepo;

	@Autowired
	ImageUtil imageUtil;
	
	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	@Autowired
	CloudinaryUtil cloudinaryUtil;
	
	@Value("${cloudinary.dirName}")
	private String dirName;
	
	public JsonDto<?> registProfile(ArtistDto artistDto) {
		JsonDto<ArtistDto> jDto = new JsonDto<ArtistDto>();

		if (artistRepo.exists(artistDto.getIdfArtist())) {
			Artist artist = artistRepo.findOne(artistDto.getIdfArtist());
			
			BeanUtil.copyProperties(artistDto, artist);			
			artistRepo.save(artist);
			BeanUtil.copyProperties(artist, artistDto);
			
			// Transactional Test
			// if (artistDto.getGender().equals("F")) {
			// throw new RuntimeException("roll back");
			// }
			
			jDto.setResultCode("S");
			jDto.setDataObject(artistDto);
		} else {
			jDto.setResultCode("F");
			jDto.setResultMessage("does not exist");
		}
		return jDto;
	}

	public JsonDto<?> registShop(ArtistShopDto artistShopDto) {

		JsonDto<ArtistShopDto> jDto = new JsonDto<ArtistShopDto>();
		ArtistShop artistShop = new ArtistShop();

		BeanUtil.copyProperties(artistShopDto, artistShop);
		artistShopRepo.save(artistShop);

		BeanUtil.copyProperties(artistShop, artistShopDto);
		jDto.setResultCode("S");
		jDto.setDataObject(artistShopDto);

		return jDto;
	}

	public JsonDto<?> registSchedule(ArtistScheduleDto artistScheduleDto) {

		JsonDto<ArtistScheduleDto> jDto = new JsonDto<ArtistScheduleDto>();
		ArtistSchedule artistSchedule = new ArtistSchedule();
		BeanUtil.copyProperties(artistScheduleDto, artistSchedule);
		artistScheduleRepo.save(artistSchedule);
		BeanUtil.copyProperties(artistSchedule, artistScheduleDto);
		jDto.setResultCode("S");
		jDto.setDataObject(artistScheduleDto);

		return jDto;
	}

	public JsonDto<?> registProfileImage(SaveProfileImageDto saveProfileImageDto) {
		JsonDto<HashMap<String, String>> jDto = new JsonDto<HashMap<String, String>>();

		if (artistRepo.exists(saveProfileImageDto.getIdfArtist())) {
			String fileName = saveProfileImageDto.getProfileImage().getOriginalFilename();
			int index = fileName.lastIndexOf(".");
			String extension = fileName.substring(index + 1);
			if (extension.equals("jpg")) {
				String idfArtist = saveProfileImageDto.getIdfArtist().toString();
				String url = cloudinaryUtil.uploadImage(dirName + "/profile", idfArtist, saveProfileImageDto.getProfileImage());
//				imageUtil.uploadImage(bucketName, "profile", idfArtist, saveProfileImageDto.getProfileImage());
				HashMap<String, String> set = new HashMap<String, String>();
				set.put("downloadUrl", url);
				jDto.setResultCode("S");
				jDto.setDataObject(set);
			} else {
				jDto.setResultCode("F");
				jDto.setResultMessage("does not support extension");
			}
		} else {
			jDto.setResultCode("F");
			jDto.setResultMessage("does not exist artist");
		}
		return jDto;
	}

	public JsonDto<?> registArtistCustomer(RegistArtistCustomerDto registArtistCustomerDto) {
		JsonDto<RegistArtistCustomerDto> jDto = new JsonDto<RegistArtistCustomerDto>();
		ArtistCustomer artistCustomer = new ArtistCustomer();
		ArtistCustomerPK artistCustomerPk = new ArtistCustomerPK();
		artistCustomerPk.setIdfArtist(registArtistCustomerDto.getIdfArtist());
		artistCustomerPk.setIdfCustomer(registArtistCustomerDto.getIdfCustomer());

		artistCustomer.setId(artistCustomerPk);
		BeanUtil.copyProperties(registArtistCustomerDto, artistCustomer);
		artistCustomerRepo.save(artistCustomer);
		jDto.setResultCode("S");
		jDto.setDataObject(registArtistCustomerDto);

		return jDto;
	}

	public JsonDto<?> readProfileByIdf(Integer idfArtist) {

		JsonDto<ArtistDto> jDto = new JsonDto<ArtistDto>();
		ArtistDto artistDto = new ArtistDto();
		if (artistRepo.exists(idfArtist)) {
			Artist artist = artistRepo.findOne(idfArtist);
			BeanUtil.copyProperties(artist, artistDto);
		}
		jDto.setResultCode("S");
		jDto.setDataObject(artistDto);
		return jDto;
	}

	public JsonDto<?> addCustomer(AddArtistCustomerDto addArtistCustomerDto) {
		JsonDto<AddArtistCustomerDto> jDto = new JsonDto<AddArtistCustomerDto>();
		ArtistCustomer artistCustomer = new ArtistCustomer();
		ArtistCustomerPK id = new ArtistCustomerPK();
		id.setIdfArtist(addArtistCustomerDto.getIdfArtist());
		id.setIdfCustomer(addArtistCustomerDto.getIdfCustomer());
		artistCustomer.setId(id);
		artistCustomerRepo.save(artistCustomer);
		jDto.setResultCode("S");
		return jDto;
	}

	public JsonDto<?> modifyReservationPolicy(ModifyReservationPolicy modifyReservationPolicy) {
		JsonDto<ModifyReservationPolicy> jDto = new JsonDto<ModifyReservationPolicy>();
		Artist artist = artistRepo.findOne(modifyReservationPolicy.getIdfArtist());
		artist.setReservationPolicy(modifyReservationPolicy.getReservationPolicy());
		artistRepo.save(artist);
		jDto.setResultCode("S");
		return jDto;
	}

	public JsonDto<?> readReservationPolicyByIdfArtist(Integer idfArtist) {
		JsonDto<ModifyReservationPolicy> jDto = new JsonDto<ModifyReservationPolicy>();

		if (artistRepo.exists(idfArtist)) {
			ModifyReservationPolicy modifyReservationPolicy = new ModifyReservationPolicy();
			Artist artist = artistRepo.findOne(idfArtist);

			modifyReservationPolicy.setIdfArtist(idfArtist);
			modifyReservationPolicy.setReservationPolicy(artist.getReservationPolicy());
			jDto.setResultCode("S");
			jDto.setDataObject(modifyReservationPolicy);
		} else {
			jDto.setResultCode("S");
			jDto.setResultMessage("does not exist artist");
		}
		return jDto;
	}

}
