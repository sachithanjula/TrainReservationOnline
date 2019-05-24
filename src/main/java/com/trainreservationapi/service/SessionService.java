package com.trainreservationapi.service;

import java.util.List;

import com.trainreservationapi.domain.Session;

public interface SessionService {

	Session getSessionOf(long uid); // returns both authkey and the uid as an Object.

	List<Session> getAdminSessions(); // returns sessions that has an admin role.

	List<Session> getUserSessions(); // returns sessions that has user role.

	long getUidOfSession(long authKey); // returns the uid of the user to whom the auth key belongs.

	String getRoleOfAuthentication(long authKey);

	boolean authenticate(long autheKey);// simply checks if the auth key exists in the db :-)

	void saveSession(Session session);

	void invokeSession(Session session);

	void invokeSessionOfUser(long uid); // delete the session entry that belongs to the uid.

	void invokeSessionOfKey(long authKey);// delete the session entry that contains the given auth key

}
