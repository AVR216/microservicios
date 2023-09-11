package com.tutorial.services.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.entity.User;
import com.tutorial.exceptions.UserGenericException;
import com.tutorial.models.Bike;
import com.tutorial.models.Car;
import com.tutorial.repositories.IUserRepository;
import com.tutorial.services.IBikeService;
import com.tutorial.services.ICarService;
import com.tutorial.services.IUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

	private final IUserRepository userRepository;

	@Override
	@Transactional
	public User save(User user) {
		boolean existsUser = this.userRepository.exists(Example.of(user));
		if (existsUser) {
			throw new UserGenericException("User already exists");
		}
		return this.userRepository.save(user);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<User> findById(Long id) {
		return this.userRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	@Override
	public Boolean existsById(Long id) {
		return this.userRepository.existsById(id);
	}

	@Override
	public Map<String, Object> getBikesAndCarsByUser(Long id, IBikeService bikeService, ICarService carService) {
		Map<String, Object> bikesAndCars = new HashMap<>();
		List<Bike> bikes = bikeService.getBikesByUserId(id);
		List<Car> cars = carService.getCarsByUserId(id);
		if (bikes.isEmpty()) {
			bikesAndCars.put("bikes", "User has not bikes");
		} else {
			bikesAndCars.put("bikes", bikes);
		}
		if (cars.isEmpty()) {
			bikesAndCars.put("cars", "User has not cars");
		} else {
			bikesAndCars.put("cars", cars);
		}
		return bikesAndCars;
	}

}
