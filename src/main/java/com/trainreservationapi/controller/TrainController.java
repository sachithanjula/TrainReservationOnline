package com.trainreservationapi.controller;


import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;


import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.trainreservationapi.config.Config;
import com.trainreservationapi.domain.Train;
import com.trainreservationapi.service.SessionServiceImpl;

import com.trainreservationapi.service.TrainServiceImpl;

@Controller
@RequestMapping(value = "/trains")
@CrossOrigin(origins = Config.allowedOrigin)
public class TrainController {

	@Autowired
	TrainServiceImpl trainService;

	@Autowired
	SessionServiceImpl sessionService = new SessionServiceImpl();

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Train>> getAllTrainWithAuth() {
		
		return new ResponseEntity<>(trainService.findAllTrain(), HttpStatus.OK);
	}


    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Train> getTrainById(@RequestHeader("Authentication") long authKey, @PathVariable("id") String id) {
        if (sessionService.authenticate(authKey)) {
            return new ResponseEntity<>(trainService.findById(id), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new Train(), HttpStatus.UNAUTHORIZED);
        }

    }

    @RequestMapping(value = "/{startStation}/{endStation}", method = RequestMethod.GET)
    public ResponseEntity<List<Train>> getTrainByStations(@PathVariable("startStation") String startStation, @PathVariable("endStation") String endStation) {
        return new ResponseEntity<>(trainService.findByStartStationAndEndStation(startStation, endStation), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<Train>> getTrainByName(@RequestHeader("Authentication") long authKey, @PathVariable String name) {
        return new ResponseEntity<>(trainService.findTrainContainingTrainName(name), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> addTrain( @RequestBody Map<String, Object> payload/*@RequestParam String name, @RequestParam int servingCount, @RequestParam ArrayList<String> ingredients, @RequestParam double price*/) {
        Train train = new Train(payload);
        trainService.saveTrain(train);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<String> updateTrain(@RequestBody Map<String, Object> payload) {
        Train trainUpdate = new Train(payload);
        trainService.updateTrain(trainUpdate);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
