package com.trainreservationapi.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Sessions")
public class Session {

	@Id
	private long uid;
	private long authKeyOfUid;
	private String role;

	/**
	 * @param uid
	 * @param authKeyOfUid
	 * @param role
	 */
	public Session(long uid, long authKeyOfUid, String role) {
		super();
		this.uid = uid;
		this.authKeyOfUid = authKeyOfUid;
		this.role = role;
	}

	public Session() {
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
	 * @return the authKeyOfUid
	 */
	public long getAuthKeyOfUid() {
		return authKeyOfUid;
	}

	/**
	 * @param authKeyOfUid the authKeyOfUid to set
	 */
	public void setAuthKeyOfUid(long authKeyOfUid) {
		this.authKeyOfUid = authKeyOfUid;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

}
