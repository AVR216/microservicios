package com.tutorial.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tutorial.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UserDTO {
	@JsonIgnore
	private Long id;
	private String name;
	private String email;
	
	public static UserDTO converEntityToDTO(User user) {
		return UserDTO.builder()
				.id(user.getId())
				.name(user.getName())
				.email(user.getEmail())
				.build();
	}
	
	public static User convertFromDtoToEntity(UserDTO userDTO) {
		return User.builder()
				.id(userDTO.getId())
				.name(userDTO.getName())
				.email(userDTO.getEmail())
				.build();
	}
}
