package com.kelompok1.securityguard.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "kejadian")
public class Kejadian {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String kejadian;
	private long level;
	private String lot;
	private String lat;
}
