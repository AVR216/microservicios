package com.tutorial.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tutorial.models.Car;

@FeignClient(name = "ms-car", url = "http://localhost:8002", path = "/ms-car/v1/car")
public interface ICarFeignClient {

	@PostMapping(path = {"/"})
	Car save(@RequestBody Car car);

	
	@GetMapping(path = {"/user-id/{userId}"})
	List<Car> getCarsByUserId(@PathVariable("userId") Long id);
}
