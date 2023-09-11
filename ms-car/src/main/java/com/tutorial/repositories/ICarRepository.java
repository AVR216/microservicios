package com.tutorial.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.entity.Car;

@Repository
public interface ICarRepository extends JpaRepository<Car, Long>{

	List<Car> findByUserId(Long userId);
	
}
