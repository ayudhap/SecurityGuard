package com.kelompok1.securityguard.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kelompok1.securityguard.entity.Friends;
import com.kelompok1.securityguard.entity.UserEntity;
import com.kelompok1.securityguard.repository.FriendsRepository;
import com.kelompok1.securityguard.repository.UserRepository;
import com.kelompok1.securityguard.request.UserFriendsListRequestEntity;
import com.kelompok1.securityguard.request.UserFriendsRequestEntity;

@Service
public class FriendsService implements FriendsRepository {
	
	@Autowired
	UserRepository userRepository;
		
	private UserEntity saveIfNotExist(String notelp) {

		UserEntity existingUser = this.userRepository.findByNoTelp(notelp);
		if (existingUser == null) {
			existingUser = new UserEntity();
			existingUser.setNoTelp(notelp);
			return this.userRepository.save(existingUser);
		} else {
			return existingUser;
		}

	}
	
	@Override
	public ResponseEntity<Map<String, Object>> addUserFriends(UserFriendsRequestEntity userFriendsRequestEntity) {

		Map<String, Object> result = new HashMap<String, Object>();

		if (userFriendsRequestEntity == null) {
			result.put("Error : ", "Invalid request");
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_REQUEST);
		}

		if (CollectionUtils.isEmpty(userFriendsRequestEntity.getFriends())) {
			result.put("Error : ", "Friend list cannot be empty");
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_REQUEST);
		}
		if (userFriendsRequestEntity.getFriends().size() != 2) {
			result.put("Info : ", "Please provide 2 emails to make them friends");
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_REQUEST);
		}

		String notelp1 = userFriendsRequestEntity.getFriends().get(0);
		String notelp2 = userFriendsRequestEntity.getFriends().get(1);

		if (notelp1 == notelp2) {
			result.put("Info : ", "Cannot make friends, if users are same");
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_REQUEST);
		}

		UserEntity user1 = null;
		UserEntity user2 = null;
		user1 = this.saveIfNotExist(notelp1);
		user2 = this.saveIfNotExist(notelp2);

		if (user1.getFriends().contains(user2)) {
			result.put("Info : ", "Can't add, they are already friends");
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		}

		user1.addUserFriends(user2);
		this.userRepository.save(user1);
		result.put("Success", true);

		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Map<String, Object>> getUserFriendsList(
			UserFriendsListRequestEntity userFriendsListRequestEntity) {

		Map<String, Object> result = new HashMap<String, Object>();

		if (userFriendsListRequestEntity == null) {
			result.put("Error : ", "Invalid request");
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_REQUEST);
		}

		UserEntity user = this.userRepository.findByEmail(userFriendsListRequestEntity.getNoTelp());
		List<String> friendList = user.getFriends().stream().map(UserEntity::getNoTelp).collect(Collectors.toList());

		result.put("success", true);
		result.put("friends", friendList);
		result.put("count", friendList.size());

		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<Map<String, Object>> getCommonUserFriends(UserFriendsRequestEntity userFriendsRequestEntity) {

		Map<String, Object> result = new HashMap<String, Object>();

		if (userFriendsRequestEntity == null) {
			result.put("Error : ", "Invalid request");
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_REQUEST);
		}

		UserEntity user1 = null;
		UserEntity user2 = null;
		user1 = this.userRepository.findByNoTelp(userFriendsRequestEntity.getFriends().get(0));
		user2 = this.userRepository.findByNoTelp(userFriendsRequestEntity.getFriends().get(1));

		if (user1.getNoTelp().equals(user2.getNoTelp())) {
			result.put("Info : ", "Both users are same");
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_REQUEST);
		}

		Set<UserEntity> friends = null;
		friends = user1.getFriends();
		friends.retainAll(user2.getFriends());

		result.put("success", true);
		result.put("friends", friends.stream().map(UserEntity::getNoTelp).collect(Collectors.toList()));
		result.put("count", friends.size());

		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
}
