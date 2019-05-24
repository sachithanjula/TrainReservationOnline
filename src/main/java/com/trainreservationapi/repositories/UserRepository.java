package com.trainreservationapi.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.trainreservationapi.domain.User;

public interface UserRepository extends MongoRepository<User, String> {

	User findByUid(long uid);

	User findByEmail(String email);

	User findByName(String name);

	List<User> findUsersByNameContaining(String name);

	void deleteByEmail(String email);

}
