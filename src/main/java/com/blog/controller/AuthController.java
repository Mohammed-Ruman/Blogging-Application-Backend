package com.blog.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.entities.User;
import com.blog.exceptions.ApiException;
import com.blog.payloads.JwtAuthRequest;
import com.blog.payloads.JwtAuthResponse;
import com.blog.payloads.UserDto;
import com.blog.security.JwtTokenHelper;
import com.blog.userservice.UserService;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@PostMapping("login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception{

		this.authenticate(request.getUsername(), request.getPassword());

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse response=new JwtAuthResponse();
		response.setToken(token);
		response.setUser(new ModelMapper().map((User)userDetails, UserDto.class));
		return new ResponseEntity<>(response,HttpStatus.OK);
	}



	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,password);

		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (AuthenticationException e) {
			throw new ApiException("Bad Credentials");
		}
	}

	@PostMapping("register")
	public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto ){
		UserDto registeredUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
	}
}
