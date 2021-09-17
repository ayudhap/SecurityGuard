package com.kelompok1.securityguard.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFriendsListRequestEntity {
	private String nama;
	private String noTelp;
}
