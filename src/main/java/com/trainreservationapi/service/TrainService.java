package com.trainreservationapi.service;

import java.util.List;

import com.trainreservationapi.domain.Train;

public interface TrainService {

	Train findById(String id);
	
	Train findByTrainName(String trainName);

	List<Train> findTrainContainingTrainName(String name); // ona getFoodByName
	
	List<Train> findByStartStationAndEndStation(String startStation, String endStation);

	double getPriceOf(String id); // ona -> payment -> getTotalAMountToPay

	void saveTrain(Train train); // ona

	void updateTrain(Train trainUpdate); // ona

	void deleteTrainById(String id); 

	List<Train> findAllTrain(); // ona -> getAllFood

	public boolean isTrainExist(Train train);

}
