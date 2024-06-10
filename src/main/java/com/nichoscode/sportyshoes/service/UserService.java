package com.nichoscode.sportyshoes.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nichoscode.sportyshoes.model.User;
import com.nichoscode.sportyshoes.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}
	
	public User findByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}

}
