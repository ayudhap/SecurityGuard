package com.kelompok1.securityguard.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import com.kelompok1.securityguard.entity.UserEntity;
import com.kelompok1.securityguard.request.UserFriendsListRequestEntity;
import com.kelompok1.securityguard.request.UserFriendsRequestEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	@Query(value = "SELECT * FROM data_user WHERE no_telp = ?1", nativeQuery = true)
	List<UserEntity> findAllByNoTelp(Set<String> notelp);

	UserEntity findByNoTelp(long notelp);

	UserEntity findByEmail(String email);
}
