package com.trainreservationapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trainreservationapi.domain.Train;
import com.trainreservationapi.repositories.TrainRepository;

@Service("trainService")
@Transactional
public class TrainServiceImpl implements TrainService{
	
	@Autowired
    private TrainRepository trainRepository;

    

	@Override
	public Train findById(String id) {
		return trainRepository.findTrainById(id);
	}

	@Override
	public List<Train> findTrainContainingTrainName(String name) {
		return null;
	}

	@Override
	public double getPriceOf(String id) {
		Train train = trainRepository.findTrainById(id);
		double price = -1;
		
		if (train != null) {
            price = train.getPrice();
        }

        return price;
	}

	@Override
	public void saveTrain(Train train) {
		trainRepository.save(train);
		
	}

	@Override
	public void updateTrain(Train trainUpdate) {
		Train trainExist = findByTrainName(trainUpdate.getTrainName());
		
		if (trainExist != null) {
			//trainExist.setIngredients(foodUpdate.getIngredients());
			trainExist.setPrice(trainUpdate.getPrice());
			trainExist.setStartStation(trainUpdate.getStartStation());
			trainExist.setEndStation(trainUpdate.getEndStation());
			trainExist.setStartTime(trainUpdate.getStartTime());;
			trainExist.setEndTime(trainUpdate.getEndTime());

            updateTrain(trainUpdate);
        }
		
	}

	@Override
	public void deleteTrainById(String id) {
		trainRepository.deleteTrainById(id);
		
	}

	@Override
	public List<Train> findAllTrain() {
		return trainRepository.findAll();
	}

	@Override
	public boolean isTrainExist(Train train) {
		return trainRepository.existsById(train.getId());
	}

	@Override
	public Train findByTrainName(String trainName) {
		return trainRepository.findTrainByTrainName(trainName);
	}

	@Override
	public List<Train> findByStartStationAndEndStation(String startStation, String endStation) {
		return trainRepository.findTrainByStartStationAndEndStation(startStation, endStation);
	}
	
	

}
