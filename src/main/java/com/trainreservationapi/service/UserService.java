package com.trainreservationapi.service;

import java.util.List;


import com.trainreservationapi.domain.User;

public interface UserService {

	User findByUid(long uid);

	User findByEmail(String email);

	long getUidOfEmail(String email);

	String getEmailOfUid(long uid);

	List<User> findUsersHavingName(String name);

	void saveUser(User user);

	void updateUser(long uid, User userUpdate);

	void deleteUserByEmail(String email);

	List<User> findAllUsers();

	boolean isUserExist(long uid);

	boolean isPasswordCorrect(String email, long password);

}
