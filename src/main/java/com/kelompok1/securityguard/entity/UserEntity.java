package com.kelompok1.securityguard.entity;

import java.util.HashSet;
import java.util.Set;

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
	private long id;
	private long nik;
	private String nama;
	private String ttl;
	private String email;
	private String noTelp;
	private String alamat;
	private String kataSandi;
	private String role;
	
	@ManyToMany
	@JoinTable(name = "teman", joinColumns = @JoinColumn(referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(referencedColumnName = "temanId"))
	private Set<UserEntity> friends;
	
	public void addUserFriends(UserEntity user) {
		if (CollectionUtils.isEmpty(this.friends)) {
			this.friends = new HashSet<>();
		}
		this.friends.add(user);
	}
}
