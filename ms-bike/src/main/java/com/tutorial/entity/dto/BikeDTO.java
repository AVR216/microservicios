package com.tutorial.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tutorial.entity.Bike;

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
public class BikeDTO {
	@JsonIgnore
	private Long id;
	private String brand;
	private String model;
	private Long userId;
	
	public static BikeDTO converEntityToDTO(Bike bike) {
		return BikeDTO.builder()
				.id(bike.getId())
				.brand(bike.getBrand())
				.model(bike.getModel())
				.userId(bike.getUserId())
				.build();
	}
	
	public static Bike convertFromDtoToEntity(BikeDTO bikeDTO) {
		return Bike.builder()
				.id(bikeDTO.getId())
				.brand(bikeDTO.getBrand())
				.model(bikeDTO.getModel())
				.userId(bikeDTO.getUserId())
				.build();
	}
}
