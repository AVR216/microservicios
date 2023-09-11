package com.tutorial.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.entity.Bike;

@Repository
public interface IBikeRepository extends JpaRepository<Bike, Long>{

	List<Bike> findByUserId(Long userId);
	
}
