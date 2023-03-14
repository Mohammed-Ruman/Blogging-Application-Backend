package com.blog.userserviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDto;
import com.blog.repository.RoleRepo;
import com.blog.repository.UserRepo;
import com.blog.userservice.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user=this.dtoToUser(userDto);
		User savedUser = ((CrudRepository<User, Integer>) userRepo).save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userid) {
		User user = this.userRepo.findById(userid).
				orElseThrow(() -> new ResourceNotFoundException("User", "Id" , userid));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());

		User updateduser = this.userRepo.save(user);
				UserDto userToDto = this.userToDto(updateduser);
		return userToDto;
	}

	@Override
	public UserDto getUserById(Integer userid) {
			User user = this.userRepo.findById(userid).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userid));

		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
			List<User> users = this.userRepo.findAll();
			List<UserDto> userDtos = users.stream().map(user-> userToDto(user)).collect(Collectors.toList());

		return userDtos;
	}

	@Override
	public void deleteUser(Integer id) {
		User user=this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "Id", id));
		this.userRepo.delete(user);
	}

	public UserDto userToDto(User user) {
		UserDto userDto=new ModelMapper().map(user, UserDto.class);

//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());

		return userDto;
	}

	public User dtoToUser(UserDto userDto) {
		User user=new ModelMapper().map(userDto, User.class);

//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());

		return user;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user=new ModelMapper().map(userDto, User.class);

		//Encoding password
		user.setPassword(this.encoder.encode(user.getPassword()));

		//Setting the role
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

		user.getRoles().add(role);

		User saveduser = this.userRepo.save(user);
		return new ModelMapper().map(saveduser, UserDto.class);
	}
}
