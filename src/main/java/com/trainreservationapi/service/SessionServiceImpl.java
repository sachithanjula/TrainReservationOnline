package com.trainreservationapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trainreservationapi.domain.Session;
import com.trainreservationapi.repositories.SessionRepository;

@Service("sessionService")
@Transactional
public class SessionServiceImpl implements SessionService {

	@Autowired
	private SessionRepository sessionRepository;

	@Override
	public Session getSessionOf(long uid) {
		return sessionRepository.findByUid(uid);
	}

	@Override
	public List<Session> getAdminSessions() {
		return sessionRepository.findByRole("admin");
	}

	@Override
	public List<Session> getUserSessions() {
		return sessionRepository.findByRole("user");

	}

	@Override
	public long getUidOfSession(long authKey) {
		Session session = sessionRepository.findByAuthKeyOfUid(authKey);
		return (session != null) ? session.getUid() : 0;
	}

	@Override
	public String getRoleOfAuthentication(long authKey) {
		Session session = sessionRepository.findByAuthKeyOfUid(authKey);
		String role = "invalid";

		if (session != null) {
			role = session.getRole();
		}

		return role;
	}

	@Override
	public boolean authenticate(long autheKey) {
		Session session = sessionRepository.findByAuthKeyOfUid(autheKey);
		return (session != null && session.getAuthKeyOfUid() == autheKey);
	}

	@Override
	public void saveSession(Session session) {
		sessionRepository.save(session);
	}

	@Override
	public void invokeSession(Session session) {
		sessionRepository.delete(session);
	}

	@Override
	public void invokeSessionOfUser(long uid) {
		sessionRepository.deleteByUid(uid);
	}

	@Override
	public void invokeSessionOfKey(long authKey) {
		sessionRepository.deleteByAuthKeyOfUid(authKey);
	}
}
