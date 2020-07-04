/**
 * 
 */
package com.freetty.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.maps.GeoApiContext;

/**
 * @Project : freetty-api-customer
 * @FileName : GoogleMapsConfig.java
 * @Date : 2017. 3. 25.
 * @작성자 : 조성훈
 * @설명 : 구글맵스 빈설정
 **/

@Configuration
public class GoogleMapsConfig {

  @Value("${google.maps.apiKey}") private String apiKey;

  @Bean
  public GeoApiContext context() {
    return new GeoApiContext().setApiKey(apiKey);
  }
}
