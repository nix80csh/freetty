package com.freetty.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.freetty.entity.Artist;
import com.freetty.repository.ArtistRepo;

@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailService {
	
	@Autowired ArtistRepo artistRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Artist account = artistRepo.findByEmail(email);
		if (account != null) {
			return new User(account.getEmail(), account.getPassword(), AuthorityUtils.createAuthorityList("Artist"));
		} else {
			throw new UsernameNotFoundException("could not find the user '" + email + "'");
		}		
	}

}
