package com.freetty.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Component
public class CloudinaryUtil {

  @Autowired Cloudinary cloudinary;

  public String uploadImage(String dirPath, String fileName, MultipartFile file) {
    String url = null;
    try {
      Map params = ObjectUtils.asMap("public_id", fileName, "folder", dirPath);
      Map resultMap = cloudinary.uploader().upload(file.getBytes(), params);
      url = (String) resultMap.get("url");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return url;
  }

  public boolean deleteImage(String dirPath, List<String> fileNameList) {
    boolean isDelete;
    try {
      List<String> fullFileNameList = new ArrayList<String>();
      for (String fullFimeName : fileNameList) {
        fullFileNameList.add(dirPath + fullFimeName);
      }
      cloudinary.api().deleteResources(fullFileNameList, ObjectUtils.emptyMap());
      isDelete = true;
    } catch (Exception e) {
      e.printStackTrace();
      isDelete = false;
    }

    return isDelete;
  }

}
