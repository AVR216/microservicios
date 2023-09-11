package com.tutorial.services.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.tutorial.entity.Car;
import com.tutorial.exceptions.CarGenericException;
import com.tutorial.repositories.ICarRepository;
import com.tutorial.services.ICarService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CarService implements ICarService {

	private final ICarRepository carRepository;

	@Override
	public Car save(Car user) {
		boolean existsUser = this.carRepository.exists(Example.of(user));
		if (existsUser) {
			throw new CarGenericException("Car already exists");
		}
		return this.carRepository.save(user);
	}

	@Override
	public Optional<Car> findById(Long id) {
		return this.carRepository.findById(id);
	}

	@Override
	public List<Car> findAll() {
		return this.carRepository.findAll();
	}

	@Override
	public List<Car> findByUserId(Long userId) {
		return this.carRepository.findByUserId(userId);
	}
}
