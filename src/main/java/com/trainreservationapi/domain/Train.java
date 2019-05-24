package com.trainreservationapi.domain;

import java.util.ArrayList;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "train")
public class Train {

	@Id
	private String id;

	private String trainName;
	private String startStation;
	private String startTime;
	private String endStation;
	private String endTime;
	private double price;
	private ArrayList<String> stations;
	private ArrayList<String> days;

	/**
	 * @param id
	 * @param trainName
	 * @param startStation
	 * @param startTime
	 * @param endStation
	 * @param endTime
	 * @param price
	 * @param stations
	 * @param days
	 */
	public Train(String trainName, String startStation, String startTime, String endStation, String endTime,
			double price, ArrayList<String> stations, ArrayList<String> days) {
		super();
		this.id = "TR" + Long.toString(trainName.hashCode());
		this.trainName = trainName;
		this.startStation = startStation;
		this.startTime = startTime;
		this.endStation = endStation;
		this.endTime = endTime;
		this.price = price;
		this.stations = stations;
		this.days = days;
	}

	public Train(Map<String, Object> payload) {
		this.trainName = payload.get("trainName").toString();
		this.startStation = payload.get("startStation").toString();
		this.startTime = payload.get("startTime").toString();
		this.endStation = payload.get("endStation").toString();
		this.endTime = payload.get("endTime").toString();
		this.id = "TR" + Long.toString(this.trainName.hashCode());
		this.price = Double.parseDouble(payload.get("price").toString());
		this.stations = getStationsList(payload.get("stations"));
		this.days = getDays(payload.get("days"));
	}

	public Train() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the trainName
	 */
	public String getTrainName() {
		return trainName;
	}

	/**
	 * @param trainName the trainName to set
	 */
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	/**
	 * @return the startStation
	 */
	public String getStartStation() {
		return startStation;
	}

	/**
	 * @param startStation the startStation to set
	 */
	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endStation
	 */
	public String getEndStation() {
		return endStation;
	}

	/**
	 * @param endStation the endStation to set
	 */
	public void setEndStation(String endStation) {
		this.endStation = endStation;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the stations
	 */
	public ArrayList<String> getStations() {
		return stations;
	}

	/**
	 * @param stations the stations to set
	 */
	public void setStations(ArrayList<String> stations) {
		this.stations = stations;
	}

	/**
	 * @return the days
	 */
	public ArrayList<String> getDays() {
		return days;
	}

	/**
	 * @param days the days to set
	 */
	public void setDays(ArrayList<String> days) {
		this.days = days;
	}

	public ArrayList<String> getStationsList(Object stations) {
		ArrayList<String> stationsList = new ArrayList<>();
		String station = stations.toString();

		station = station.replace("[", "");
		station = station.replace("]", "");
		String[] stationArray = station.split(",");

		for (String stationsLoop : stationArray) {

			if (stationsLoop.startsWith(" ")) {
				stationsLoop = stationsLoop.replaceFirst(" ", "");
			}
			stationsList.add(stationsLoop);
		}

		return stationsList;
	}

	public ArrayList<String> getDays(Object days) {
		ArrayList<String> dayList = new ArrayList<>();
		String day = days.toString();

		day = day.replace("[", "");
		day = day.replace("]", "");

		String[] dayArray = day.split(",");

		for (String daysLoop : dayArray) {

			if (daysLoop.startsWith(" ")) {
				daysLoop = daysLoop.replaceFirst(" ", "");
			}
			dayList.add(daysLoop);
		}

		return dayList;
	}

}
