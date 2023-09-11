package com.tutorial.services.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.tutorial.entity.Bike;
import com.tutorial.exceptions.bIKEGenericException;
import com.tutorial.repositories.IBikeRepository;
import com.tutorial.services.IBikeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BikeService implements IBikeService {

	private final IBikeRepository bikeRepository;

	@Override
	public Bike save(Bike user) {
		boolean existsUser = this.bikeRepository.exists(Example.of(user));
		if (existsUser) {
			throw new bIKEGenericException("Bike already exists");
		}
		return this.bikeRepository.save(user);
	}

	@Override
	public Optional<Bike> findById(Long id) {
		return this.bikeRepository.findById(id);
	}

	@Override
	public List<Bike> findAll() {
		return this.bikeRepository.findAll();
	}

	@Override
	public List<Bike> findByUserId(Long userId) {
		return this.bikeRepository.findByUserId(userId);
	}
}
