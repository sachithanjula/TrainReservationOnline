package com.trainreservationapi.domain;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "TicketBookings")
public class Payment {

	@Id
	private long pid;

	private long uid;
	private double amount;
	private Map<String, Integer> ticketsAndCounts;
	private String paymentType;
	private Date paymentDate;
	private Map<String, String> paymentDetails;

	/**
	 * @param pid
	 * @param uid
	 * @param amount
	 * @param ticketsAndCounts
	 * @param paymentType
	 * @param paymentDate
	 * @param paymentDetails
	 */
	public Payment(long pid, long uid, double amount, Map<String, Integer> ticketsAndCounts, String paymentType,
			Date paymentDate, Map<String, String> paymentDetails) {
		super();
		this.pid = pid;
		this.uid = uid;
		this.amount = amount;
		this.ticketsAndCounts = ticketsAndCounts;
		this.paymentType = paymentType;
		this.paymentDate = paymentDate;
		this.paymentDetails = paymentDetails;
	}

	public Payment() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the pid
	 */
	public long getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	public void setPid(long pid) {
		this.pid = pid;
	}

	/**
	 * @return the uid
	 */
	public long getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(long uid) {
		this.uid = uid;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the ticketsAndCounts
	 */
	public Map<String, Integer> getTicketsAndCounts() {
		return ticketsAndCounts;
	}

	/**
	 * @param ticketsAndCounts the ticketsAndCounts to set
	 */
	public void setTicketsAndCounts(Map<String, Integer> ticketsAndCounts) {
		this.ticketsAndCounts = ticketsAndCounts;
	}

	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return the paymentDate
	 */
	public Date getPaymentDate() {
		return paymentDate;
	}

	/**
	 * @param paymentDate the paymentDate to set
	 */
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	/**
	 * @return the paymentDetails
	 */
	public Map<String, String> getPaymentDetails() {
		return paymentDetails;
	}

	/**
	 * @param paymentDetails the paymentDetails to set
	 */
	public void setPaymentDetails(Map<String, String> paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

}
