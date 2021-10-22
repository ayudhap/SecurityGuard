package com.kelompok1.securityguard.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.kelompok1.securityguard.config.JwtTokenUtil;
import com.kelompok1.securityguard.entity.UserEntity;
import com.kelompok1.securityguard.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	UserService userService;

	@GetMapping("/getalluser")
	public ResponseEntity<?> getAllUser() {
		return ResponseEntity.ok(userService.getAllUser());
	}

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody UserEntity userEntity) throws Exception {
		authenticate(userEntity.getEmail(), userEntity.getKataSandi());

		final UserDetails userDetails = userService.loadUserByUsername(userEntity.getEmail());
		UserEntity user = userService.getUserByEmail(userEntity.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails, user.getKataSandi(), user.getId(), user.getNama(),
				user.getTempatLahir(), user.getTanggalLahir(), user.getNoTelp(), user.getAlamat(), user.getEmail(),
				user.getNik(), user.getJenisKelamin());

		return ResponseEntity.ok(token);
	}

	@PostMapping("/register")
	public ResponseEntity<?> createUser(@ModelAttribute(value = "data") String dataJSON) throws IOException {
		UserEntity user = new Gson().fromJson(dataJSON, UserEntity.class);

		BCryptPasswordEncoder passwordEncode = new BCryptPasswordEncoder();
		user.setKataSandi(passwordEncode.encode(user.getKataSandi()));
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
				.body(this.userService.save(user));
	}

	private void authenticate(String email, String kataSandi) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, kataSandi));
		} catch (DisabledException e) {
			// TODO: handle exception
			throw new Exception("USER DISABLED", e);
		} catch (BadCredentialsException e) {
			// TODO: handle exception
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
