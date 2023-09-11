package com.tutorial.services.imp;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.tutorial.feignclients.ICarFeignClient;
import com.tutorial.models.Car;
import com.tutorial.services.ICarService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarService implements ICarService {

	private final RestTemplate restTemplate;
	private final ICarFeignClient carFeignClient;

	@Override
	public List<Car> getAll(Long id) {
		ParameterizedTypeReference<List<Car>> typeRef = new ParameterizedTypeReference<List<Car>>() {
		};
		List<Car> carList = null;
		try {
			ResponseEntity<List<Car>> response = this.restTemplate
					.exchange("http://localhost:8002/ms-car/v1/car/user-id/" + id, HttpMethod.GET, null, typeRef);
			carList = response.getBody();
		} catch (RestClientException e) {
			log.error("An http error while petition has ocurred - {1}", e.getMessage());
		}
		return carList;
	}

	@Override
	public Car save(Long userId, Car car) {
		car.setUserId(userId);
		return this.carFeignClient.save(car);
	}

	@Override
	public List<Car> getCarsByUserId(Long id) {
		return this.carFeignClient.getCarsByUserId(id);
	}
}
