package com.tutorial.services;

import java.util.List;
import java.util.Optional;

import com.tutorial.entity.Bike;

public interface IBikeService {

	Bike save(Bike bike);
	Optional<Bike> findById(Long id);
	List<Bike> findAll();
	List<Bike> findByUserId(Long id);
}
