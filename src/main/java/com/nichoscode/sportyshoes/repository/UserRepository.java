package com.nichoscode.sportyshoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nichoscode.sportyshoes.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findUserByEmail(String email);
}
