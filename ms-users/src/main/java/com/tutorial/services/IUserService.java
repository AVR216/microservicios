package com.tutorial.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.tutorial.entity.User;

public interface IUserService {

	User save(User user);
	Optional<User> findById(Long id);
	List<User> findAll();
	Boolean existsById(Long id);
	Map<String, Object> getBikesAndCarsByUser(Long id, IBikeService bikeService, ICarService carService);
}
