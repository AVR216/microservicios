package com.tutorial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{

}
