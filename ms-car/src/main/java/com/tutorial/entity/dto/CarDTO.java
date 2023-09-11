package com.tutorial.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tutorial.entity.Car;

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
public class CarDTO {
	@JsonIgnore
	private Long id;
	private String brand;
	private String model;
	private Long userId;
	
	public static CarDTO converEntityToDTO(Car car) {
		return CarDTO.builder()
				.id(car.getId())
				.brand(car.getBrand())
				.model(car.getModel())
				.userId(car.getUserId())
				.build();
	}
	
	public static Car convertFromDtoToEntity(CarDTO carDTO) {
		return Car.builder()
				.id(carDTO.getId())
				.brand(carDTO.getBrand())
				.model(carDTO.getModel())
				.userId(carDTO.getUserId())
				.build();
	}
}
