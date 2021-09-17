package com.kelompok1.securityguard.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kelompok1.securityguard.entity.UserEntity;
import com.kelompok1.securityguard.repository.UserRepository;
import com.kelompok1.securityguard.request.UserFriendsListRequestEntity;
import com.kelompok1.securityguard.request.UserFriendsRequestEntity;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserEntity user = userRepository.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException("User tidak ditemukan dengan email : " + email);
		}

		return new User(user.getEmail(), user.getKataSandi(), new ArrayList<>());
	}

	public List<UserEntity> getAllUser() {
		return userRepository.findAll();
	}

	public UserEntity getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public String save(UserEntity user) {
		userRepository.save(user);
		return "Berhasil menambahkan";
	}

	public List<UserEntity> getUserByNoTelp(Set<String> notelp) {
		return userRepository.findAllByNoTelp(notelp);
	}
}
