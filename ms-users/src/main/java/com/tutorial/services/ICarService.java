package com.tutorial.services;

import java.util.List;

import com.tutorial.models.Car;

public interface ICarService {

	List<Car> getAll(Long id);
	public Car save(Long userId, Car car);
	List<Car> getCarsByUserId(Long id);
}
