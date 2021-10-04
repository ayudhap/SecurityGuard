package com.kelompok1.securityguard.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.util.CollectionUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "user")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;
	@Column(length = 255)
	private String nama;
	@Column(length = 50)
	private String ttl;
	@Column(length = 255)
	private String email;
	@Column(unique = true)
	private String noTelp;
	@Column(length = 255)
	private String alamat;
	private String kataSandi;
	@Column(length = 50)
	private String role;
	
	@ManyToMany
	@JoinTable(name = "friends", joinColumns = @JoinColumn(name = "userId") , inverseJoinColumns = @JoinColumn(name = "friendId"))
	private Set<UserEntity> friends;
	
	public void addUserFriends(UserEntity user) {
		if (CollectionUtils.isEmpty(this.friends)) {
			this.friends = new HashSet<>();
		}
		this.friends.add(user);
	}
}
