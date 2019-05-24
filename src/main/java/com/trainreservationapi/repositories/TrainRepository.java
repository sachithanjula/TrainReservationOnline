package com.trainreservationapi.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.trainreservationapi.domain.Train;

public interface TrainRepository extends MongoRepository<Train, String> {

	Train findTrainByTrainName(String trainName);

	Train findTrainById(String id);

	List<Train> findTrainByStartStationAndEndStation(String startStation, String endStation);

	void deleteTrainById(String id);

	void deleteTrainByTrainName(String trainName);

}
