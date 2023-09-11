package com.tutorial.services;

import java.util.List;

import com.tutorial.models.Bike;


public interface IBikeService {
	List<Bike> getAll(Long id);
	Bike save(Long userId, Bike bike);
	List<Bike> getBikesByUserId(Long id);
}
