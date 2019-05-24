package com.trainreservationapi.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.trainreservationapi.domain.Session;

public interface SessionRepository extends MongoRepository<Session, String> {

	Session findByUid(long uid);

	Session findByAuthKeyOfUid(long authKey);

	List<Session> findByRole(String role);

	void deleteByAuthKeyOfUid(long authKey);

	void deleteByUid(long uid);

}
