package com.blog.userservice;

import java.util.List;

import com.blog.payloads.UserDto;

public interface UserService {

	public UserDto createUser(UserDto userDto);
	UserDto updateUser(UserDto userDto, Integer userid);
	UserDto getUserById(Integer userid);
	List<UserDto> getAllUsers();
	void deleteUser(Integer id);

	UserDto registerNewUser(UserDto userDto);

}
