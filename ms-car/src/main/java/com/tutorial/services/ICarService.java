package com.tutorial.services;

import java.util.List;
import java.util.Optional;

import com.tutorial.entity.Car;

public interface ICarService {

	Car save(Car user);
	Optional<Car> findById(Long id);
	List<Car> findAll();
	List<Car> findByUserId(Long id);
}
