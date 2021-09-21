package com.kelompok1.securityguard.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFriendsRequestEntity {
	private List<Long> friends;
}
