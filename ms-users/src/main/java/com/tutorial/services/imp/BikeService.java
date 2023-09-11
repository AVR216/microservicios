package com.tutorial.services.imp;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.tutorial.feignclients.IBikeFeignClient;
import com.tutorial.models.Bike;
import com.tutorial.services.IBikeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BikeService implements IBikeService {

	private final RestTemplate restTemplate;
	private final IBikeFeignClient bikeFeignClient;

	@Override
	public List<Bike> getAll(Long id) {
		ParameterizedTypeReference<List<Bike>> typeRef = new ParameterizedTypeReference<List<Bike>>() {
		};
		List<Bike> bikeList = null;
		try {
			ResponseEntity<List<Bike>> response = this.restTemplate
					.exchange("http://localhost:8004/ms-bike/v1/bike/user-id/" + id, HttpMethod.GET, null, typeRef);
			bikeList = response.getBody();
		} catch (RestClientException e) {
			log.error("An http error while petition has ocurred - {1}", e.getMessage());
		}
		return bikeList;
	}

	@Override
	public Bike save(Long userId, Bike bike) {
		bike.setUserId(userId);
		return this.bikeFeignClient.save(bike);
	}

	@Override
	public List<Bike> getBikesByUserId(Long id) {
		return this.bikeFeignClient.getBikesByUserId(id);
	}

}
