package com.trainreservationapi.domain;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
public class User {

	@Id
	private long uid = 0; // is auto generated.

	
	private String email;


	private String name;


	private int mobileNumber;


	private String address;

	private long password;


	/**
	 * @param uid
	 * @param email
	 * @param name
	 * @param mobileNumber
	 * @param address
	 * @param password
	 */
	public User(long uid, @NotEmpty String email,  String name,  int mobileNumber,
			 String address, long password) {
		super();
		this.uid = uid;
		this.email = email;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.address = address;
		this.password = password;
		
	}

	public User() {
		// TODO Auto-generated constructor stub
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the mobileNumber
	 */
	public int getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(int mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the password
	 */
	public long getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(long password) {
		this.password = password;
	}

	

}
