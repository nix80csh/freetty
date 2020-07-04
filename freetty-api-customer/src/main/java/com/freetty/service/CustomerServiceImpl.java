package com.freetty.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freetty.common.BeanUtil;
import com.freetty.common.CloudinaryUtil;
import com.freetty.common.ImageUtil;
import com.freetty.dto.CustomerDto;
import com.freetty.dto.CustomerDto.SaveProfileImageDto;
import com.freetty.dto.JsonDto;
import com.freetty.entity.Customer;
import com.freetty.repository.CustomerRepo;

@Service
@Transactional
public class CustomerServiceImpl {

  @Autowired CustomerRepo customerRepo;
  @Autowired ImageUtil imageUtil;
  @Value("${cloud.aws.s3.bucket}") private String bucketName;
  @Autowired CloudinaryUtil cloudinaryUtil;
  @Value("${cloudinary.dirName}") private String dirName;


  public JsonDto<?> registProfile(CustomerDto customerDto) {
    JsonDto<CustomerDto> jDto = new JsonDto<CustomerDto>();

    if (customerRepo.exists(customerDto.getIdfCustomer())) {

      Customer customer = customerRepo.findOne(customerDto.getIdfCustomer());
      Customer otherCustomer = customerRepo.findByMobile(customerDto.getMobile());
      if (otherCustomer == null) {
        BeanUtil.copyProperties(customerDto, customer);
        customerRepo.save(customer);
        BeanUtil.copyProperties(customer, customerDto);

        // Transactional Test
        // if (artistDto.getGender().equals("F")) {
        // throw new RuntimeException("roll back");
        // }

        jDto.setResultCode("S");
        jDto.setDataObject(customerDto);
      } else if (otherCustomer != null
          && otherCustomer.getIdfCustomer() == customer.getIdfCustomer()) {
        BeanUtil.copyProperties(customerDto, customer);
        customerRepo.save(customer);
        BeanUtil.copyProperties(customer, customerDto);

        // Transactional Test
        // if (artistDto.getGender().equals("F")) {
        // throw new RuntimeException("roll back");
        // }

        jDto.setResultCode("S");
        jDto.setDataObject(customerDto);
      } else {
        jDto.setResultCode("F");
        jDto.setResultMessage("duplicate mobile");
      }

    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist customer");
    }
    return jDto;
  }

  public JsonDto<?> registProfileImage(SaveProfileImageDto saveProfileImageDto) {
    JsonDto<HashMap<String, String>> jDto = new JsonDto<HashMap<String, String>>();

    if (customerRepo.exists(saveProfileImageDto.getIdfCustomer())) {
      String fileName = saveProfileImageDto.getProfileImage().getOriginalFilename();
      int index = fileName.lastIndexOf(".");
      String extension = fileName.substring(index + 1);
      if (extension.equals("jpg")) {
        String idfArtist = saveProfileImageDto.getIdfCustomer().toString();
        String url = cloudinaryUtil.uploadImage(dirName + "/profile", idfArtist,
            saveProfileImageDto.getProfileImage());
        // imageUtil.uploadImage(bucketName, "profile", idfArtist,
        // saveProfileImageDto.getProfileImage());
        HashMap<String, String> set = new HashMap<String, String>();
        set.put("downloadUrl", url);
        jDto.setResultCode("S");
        jDto.setDataObject(set);

        // Timestamp cloudStamp = new Timestamp(1489048090);
        // Timestamp stamp = new Timestamp(System.currentTimeMillis());
        // System.out.println(stamp);
        // System.out.println(cloudStamp);
        //
        // System.out.println(System.currentTimeMillis());
        // System.out.println("1489048090986");
        //
        // Date date = new Date(stamp.getTime());
        // System.out.println(date);

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

  public JsonDto<?> readProfileByIdf(Integer idfCustomer) {

    JsonDto<CustomerDto> jDto = new JsonDto<CustomerDto>();
    CustomerDto customerDto = new CustomerDto();
    if (customerRepo.exists(idfCustomer)) {
      Customer customer = customerRepo.findOne(idfCustomer);
      BeanUtil.copyProperties(customer, customerDto);
    }
    jDto.setResultCode("S");
    jDto.setDataObject(customerDto);
    return jDto;
  }

  public JsonDto<?> checkMobileValidation(Integer idfCustomer) {
    JsonDto<Map<String, String>> jDto = new JsonDto<Map<String, String>>();
    if (customerRepo.exists(idfCustomer)) {
      Customer customer = customerRepo.findOne(idfCustomer);
      if (customer.getMobile() != null) {
        jDto.setResultCode("S");
      } else {
        jDto.setResultCode("F");
        jDto.setResultMessage("fail to mobile validation");
      }
    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist customer");
    }

    return jDto;
  }

}
