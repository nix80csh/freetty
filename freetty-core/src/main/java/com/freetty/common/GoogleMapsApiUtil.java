/**
 * 
 */
package com.freetty.common;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

/**
 * @Project : freetty-core
 * @FileName : GoogleMapsApiUtil.java
 * @Date : 2017. 3. 25.
 * @작성자 : 조성훈
 * @설명 : 구글맵스를 편리하게 사용하기 위한 유틸
 **/

public class GoogleMapsApiUtil {

  @Autowired GeoApiContext geoApiContext;

  public LatLng getLatLng(String addr1) {

    try {
      GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, addr1).await();
      return results[0].geometry.location;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }
}
