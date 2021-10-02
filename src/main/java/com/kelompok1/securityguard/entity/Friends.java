package com.kelompok1.securityguard.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "friends")
public class Friends {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long friendId;
	
	@ManyToOne
	@JoinColumn(name = "Id", nullable = false)
	private UserEntity user;
}
