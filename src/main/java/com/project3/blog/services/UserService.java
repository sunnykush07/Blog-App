package com.project3.blog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project3.blog.entities.User;
import com.project3.blog.payloads.UserDto;

@Service
public interface UserService {
	UserDto registerUser(UserDto user);
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Integer userId);
	UserDto getUserById(Integer userId);
	void deleteUser(Integer userId);
	List<UserDto> getAllUsers();
}
