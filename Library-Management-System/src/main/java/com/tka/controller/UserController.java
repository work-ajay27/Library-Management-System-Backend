package com.tka.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tka.entity.User;
import com.tka.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class UserController {

	@Autowired
	private UserRepository userRepository;

//	It is for Sign Up Part
	@PostMapping("/signup")
	public Map<String, String> signup(@RequestBody User user) {
		Map<String, String> response = new HashMap<>();
		if (userRepository.existsByEmail(user.getEmail())) {
			response.put("message", "Email already exists");
		} else {
			userRepository.save(user);
			response.put("message", "Signup successful");
		}

		return response;
	}

//	It is for Log in Part
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User loginRequest) {
		User existingUser = userRepository.findByEmail(loginRequest.getEmail());

		if (existingUser != null && existingUser.getPassword().equals(loginRequest.getPassword())) {
			return ResponseEntity.ok(existingUser); 
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid email or password"));
		}
	}

}
