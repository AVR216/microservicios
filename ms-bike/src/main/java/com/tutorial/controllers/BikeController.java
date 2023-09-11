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

import com.tutorial.entity.Bike;
import com.tutorial.entity.dto.BikeDTO;
import com.tutorial.exceptions.bIKEGenericException;
import com.tutorial.services.IBikeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(path = { "/ms-bike/v1/bike" })
@RestController
public class BikeController {

	private final IBikeService bikeService;

	@PostMapping(path = { "/" })
	ResponseEntity<Object> save(@RequestBody BikeDTO bikeDTO) {
		try {
			var bikeSaved = BikeDTO.convertFromDtoToEntity(bikeDTO);
			bikeSaved = this.bikeService.save(bikeSaved);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(bikeSaved.getId()).toUri();
			return ResponseEntity.created(location).body(BikeDTO.converEntityToDTO(bikeSaved));
		} catch (bIKEGenericException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("Internal server error: " + e.getMessage());
		}
	}

	@GetMapping(path = { "/{id}" })
	ResponseEntity<Object> getById(@PathVariable("id") Long id) {
		Optional<Bike> carOptional = this.bikeService.findById(id);
		if (!carOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(carOptional.get());
	}

	@GetMapping(path = { "/" })
	ResponseEntity<Object> getAll() {
		return ResponseEntity.ok(this.bikeService.findAll());
	}
	
	@GetMapping(path = {"/user-id/{id}"})
	ResponseEntity<Object> getCarsByUserId(@PathVariable("id") Long userId) {
		return ResponseEntity.ok(this.bikeService.findByUserId(userId));
	}
}
