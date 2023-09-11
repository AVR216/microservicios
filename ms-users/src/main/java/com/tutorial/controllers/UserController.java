package com.tutorial.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tutorial.entity.User;
import com.tutorial.entity.dto.UserDTO;
import com.tutorial.exceptions.UserGenericException;
import com.tutorial.models.Bike;
import com.tutorial.models.Car;
import com.tutorial.services.IBikeService;
import com.tutorial.services.ICarService;
import com.tutorial.services.IUserService;

import feign.FeignException.FeignClientException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(path = { "/ms-users/v1/user" })
@RestController
public class UserController {

	private final IUserService userService;
	private final ICarService carService;
	private final IBikeService bikeService;

	@PostMapping(path = { "/" })
	ResponseEntity<Object> save(@RequestBody UserDTO userDTO) {
		try {
			var userSaved = UserDTO.convertFromDtoToEntity(userDTO);
			userSaved = this.userService.save(userSaved);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(userSaved.getId()).toUri();
			return ResponseEntity.created(location).body(UserDTO.converEntityToDTO(userSaved));
		} catch (UserGenericException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("Internal server error: " + e.getMessage());
		}
	}

	@GetMapping(path = { "/{id}" })
	ResponseEntity<Object> getById(@PathVariable("id") Long id) {
		Optional<User> userOptional = this.userService.findById(id);
		if (!userOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(userOptional.get());
	}

	@GetMapping(path = { "/" })
	ResponseEntity<Object> getAll() {
		return ResponseEntity.ok(this.userService.findAll());
	}

	@GetMapping(path = { "/get-cars/{userId}" })
	ResponseEntity<List<Car>> getCars(@PathVariable("userId") Long userid) {
		return this.userService.findById(userid)
				.map(user -> new ResponseEntity<>(this.carService.getAll(userid), HttpStatus.OK))
				.orElse(ResponseEntity.notFound().build());

	}

	@GetMapping(path = { "/get-bikes/{userId}" })
	ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") Long userId) {
		return this.userService.findById(userId).map(user -> ResponseEntity.ok(this.bikeService.getAll(user.getId())))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping(path = {"/save-car/{userId}"})
	ResponseEntity<Object> saveCar(@PathVariable("userId") Long userId, @RequestBody Car car) {
		if(Boolean.FALSE.equals(this.userService.existsById(userId))) {
			return ResponseEntity.notFound().build();
		}
		try {
			return ResponseEntity.ok(this.carService.save(userId, car));			
		}catch (FeignClientException e) {
			return new ResponseEntity<>("Car already exists", HttpStatus.CONFLICT);
		}
	}
	
	@PostMapping(path = {"/save-bike/{userId}"})
	ResponseEntity<Object> saveBike(@PathVariable("userId") Long userId, @RequestBody Bike bike) {
		if(Boolean.FALSE.equals(this.userService.existsById(userId))) {
			return ResponseEntity.notFound().build();
		}
		try {
			return ResponseEntity.ok(this.bikeService.save(userId, bike));			
		}catch (FeignClientException e) {
			return new ResponseEntity<>("Bike already exists", HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping(path = {"/bikes-and-cars/{userId}"})
	ResponseEntity<Object> getBikesAndCars(@PathVariable("userId") Long userId) {
		if(Boolean.FALSE.equals(this.userService.existsById(userId))) {
			return ResponseEntity.notFound().build();
		}
		try {
			return ResponseEntity.ok(this.userService.getBikesAndCarsByUser(userId, this.bikeService, this.carService));			
		}catch (FeignClientException e) {
			return new ResponseEntity<>("Error has ocurred", HttpStatus.BAD_REQUEST);
		}
	}
}
