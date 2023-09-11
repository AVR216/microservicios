package com.tutorial.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tutorial.models.Bike;

@FeignClient(name = "ms-bike", url = "http://localhost:8004", path = "/ms-bike/v1/bike")
public interface IBikeFeignClient {

	@PostMapping(path = {"/"})
	Bike save(@RequestBody Bike bike);
	
	@GetMapping(path = {"/user-id/{userId}"})
	List<Bike> getBikesByUserId(@PathVariable("userId") Long userId);
}
