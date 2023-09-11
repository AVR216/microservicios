package com.tutorial.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tutorial.entity.Car;
import com.tutorial.entity.dto.CarDTO;
import com.tutorial.exceptions.CarGenericException;
import com.tutorial.services.ICarService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(path = { "/ms-car/v1/car" })
@RestController
public class CarController {

	private final ICarService carService;

	@PostMapping(path = { "/" })
	ResponseEntity<Object> save(@RequestBody CarDTO carDTO) {
		try {
			var carSaved = CarDTO.convertFromDtoToEntity(carDTO);
			carSaved = this.carService.save(carSaved);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(carSaved.getId()).toUri();
			return ResponseEntity.created(location).body(CarDTO.converEntityToDTO(carSaved));
		} catch (CarGenericException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("Internal server error: " + e.getMessage());
		}
	}

	@GetMapping(path = { "/{id}" })
	ResponseEntity<Object> getById(@PathVariable("id") Long id) {
		Optional<Car> carOptional = this.carService.findById(id);
		if (!carOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(carOptional.get());
	}

	@GetMapping(path = { "/" })
	ResponseEntity<Object> getAll() {
		return ResponseEntity.ok(this.carService.findAll());
	}
	
	@GetMapping(path = {"/user-id/{id}"})
	ResponseEntity<Object> getCarsByUserId(@PathVariable("id") Long userId) {
		return ResponseEntity.ok(this.carService.findByUserId(userId));
	}
}
