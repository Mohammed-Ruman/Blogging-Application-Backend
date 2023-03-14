package com.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Data
public class UserDto {

	private int id;

	@NotEmpty(message = "Username can't be empty")
	@Size(min = 4, message = "Username must contain min 4 chars")
	private String name;

	@Email(message = "Email address is invalid")
	@NotEmpty(message="Email can't be empty")
	private String email;

	@NotEmpty(message = "Password can't be empty")
	@Size(min = 5, max = 10, message = "Password should be in between 5 and 10 chars")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@NotEmpty(message="About can't be empty")
	private String about;

	private Set<RoleDto> roles=new HashSet<>();

}
