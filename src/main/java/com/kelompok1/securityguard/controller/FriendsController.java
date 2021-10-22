package com.kelompok1.securityguard.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kelompok1.securityguard.repository.FriendsRepository;
import com.kelompok1.securityguard.request.UserFriendsListRequestEntity;
import com.kelompok1.securityguard.request.UserFriendsRequestEntity;

@RestController
@RequestMapping("/friends")
public class FriendsController {

	@Autowired
	FriendsRepository friendsRepository;
	
	@RequestMapping(value = "/userFriendRequest", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> userFriendRequest(@RequestBody UserFriendsRequestEntity userFriendsRequestEntity) {
		return this.friendsRepository.addUserFriends(userFriendsRequestEntity);
	}
	
	@RequestMapping(value = "/getUserFriendList", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getUserFriendList(@RequestBody UserFriendsListRequestEntity userFriendsListRequestEntity) {
		return this.friendsRepository.getUserFriendsList(userFriendsListRequestEntity);
	}

	@RequestMapping(value = "/getCommonUserFriends", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getCommonUserFriends(@RequestBody UserFriendsRequestEntity userFriendsRequestEntity) {
		return this.friendsRepository.getCommonUserFriends(userFriendsRequestEntity);
	}
	
}
