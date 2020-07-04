package com.freetty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.freetty.dto.ArtistCustomerDto.RegistArtistCustomerDto;
import com.freetty.dto.ArtistDto;
import com.freetty.dto.ArtistDto.AddArtistCustomerDto;
import com.freetty.dto.ArtistDto.ModifyReservationPolicy;
import com.freetty.dto.ArtistDto.SaveProfileImageDto;
import com.freetty.dto.ArtistScheduleDto;
import com.freetty.dto.ArtistShopDto;
import com.freetty.dto.JsonDto;
import com.freetty.service.ArtistServiceImpl;

@RestController
@RequestMapping("/artist")
public class ArtistController extends BaseController {

  @Autowired ArtistServiceImpl artistService;

  @RequestMapping(value = "/registProfile", method = RequestMethod.POST)
  public ResponseEntity<?> registProfile(@RequestBody ArtistDto artistDto) {
    JsonDto<?> jDto = artistService.registProfile(artistDto);
    return new ResponseEntity<>(jDto, HttpStatus.OK);
  }

  @RequestMapping(value = "/registProfileImage", method = RequestMethod.POST)
  public ResponseEntity<?> registProfileImage(SaveProfileImageDto saveProfileImageDto) {
    JsonDto<?> jDto = artistService.registProfileImage(saveProfileImageDto);
    return new ResponseEntity<>(jDto, HttpStatus.OK);
  }

  @RequestMapping(value = "/registShop", method = RequestMethod.POST)
  public ResponseEntity<?> registShop(@RequestBody ArtistShopDto artistShopDto) {
    JsonDto<?> jDto = artistService.registShop(artistShopDto);
    return new ResponseEntity<>(jDto, HttpStatus.OK);
  }

  @RequestMapping(value = "/registSchedule", method = RequestMethod.POST)
  public ResponseEntity<?> registSchedule(@RequestBody ArtistScheduleDto artistScheduleDto) {
    JsonDto<?> jDto = artistService.registSchedule(artistScheduleDto);
    return new ResponseEntity<>(jDto, HttpStatus.OK);
  }

  @RequestMapping(value = "/registArtistCustomer", method = RequestMethod.POST)
  public ResponseEntity<?> registArtistCustomer(
      @RequestBody RegistArtistCustomerDto registArtistCustomerDto) {
    JsonDto<?> jDto = artistService.registArtistCustomer(registArtistCustomerDto);
    return new ResponseEntity<>(jDto, HttpStatus.OK);
  }

  @RequestMapping(value = "/readProfileByIdf/{idfArtist}", method = RequestMethod.GET)
  public ResponseEntity<?> readProfileByIdf(@PathVariable Integer idfArtist) {
    JsonDto<?> jDto = artistService.readProfileByIdf(idfArtist);
    return new ResponseEntity<>(jDto, HttpStatus.OK);
  }


  @RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
  public ResponseEntity<?> addCustomer(@RequestBody AddArtistCustomerDto addArtistCustomerDto) {
    JsonDto<?> jDto = artistService.addCustomer(addArtistCustomerDto);
    return new ResponseEntity<>(jDto, HttpStatus.OK);
  }

  @RequestMapping(value = "/modifyReservationPolicy", method = RequestMethod.POST)
  public ResponseEntity<?> modifyReservationPolicy(
      @RequestBody ModifyReservationPolicy modifyReservationPolicy) {
    JsonDto<?> jDto = artistService.modifyReservationPolicy(modifyReservationPolicy);
    return new ResponseEntity<>(jDto, HttpStatus.OK);
  }

  @RequestMapping(value = "/readReservationPolicyByIdfArtist/{idfArtist}",
      method = RequestMethod.GET)
  public ResponseEntity<?> readReservationPolicyByIdfArtist(@PathVariable Integer idfArtist) {
    JsonDto<?> jDto = artistService.readReservationPolicyByIdfArtist(idfArtist);
    return new ResponseEntity<>(jDto, HttpStatus.OK);
  }



}
