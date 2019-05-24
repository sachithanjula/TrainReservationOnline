package com.trainreservationapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.concurrent.ThreadLocalRandom;


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
import com.trainreservationapi.domain.Session;
import com.trainreservationapi.domain.User;

import com.trainreservationapi.service.SessionServiceImpl;

import com.trainreservationapi.service.UserServiceImpl;

@Controller
@RequestMapping(value = "/users")
@CrossOrigin(origins = Config.allowedOrigin)
public class UserController {

	@Autowired
	private UserServiceImpl userService = new UserServiceImpl();

	@Autowired
	private SessionServiceImpl sessionService = new SessionServiceImpl();

	// GET all users in the database.
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUsers() {
		System.out.println(userService.findAllUsers()+"1234");
		return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
		
	}

	// GET specific user by its uid, email or name.
	// never send the actual password back; use the password related method for
	// that.
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserByUid(@PathVariable("id") long uid) {
		User user = userService.findByUid(uid);
		user.setPassword(0);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	// GET a random user. This is for selecting a winner for staying at a luxry
	// hotel.
	// only admins with administrative authentication keys can call this.
	@RequestMapping(value = "/random", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> selectWinner(@RequestHeader("Authentication") long authKey) {
		// we need to verify both the authentication key itself and that it belongs to
		// an admin as well.
		String role = sessionService.getRoleOfAuthentication(authKey);
		User winner = new User();
		Map<String, Object> response = new HashMap<>();

		if (role.equals("admin")) {
			List<User> users = userService.findAllUsers();
			winner = users.get(ThreadLocalRandom.current().nextInt(0, users.size() - 1));

			response.put("success", "true");
			response.put("data", winner);

			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.put("success", "false");
			response.put("data", "Unauthorized");

			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}

	}

	@RequestMapping(value = "/email/{email}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
		User user = userService.findByEmail(email);
		user.setPassword(0);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUserByName(@PathVariable("name") String name) {
		List<User> users = userService.findUsersHavingName(name);
		users.forEach(user -> user.setPassword(0)); // replace everyone's password with an empty string.

		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	// ADD a new user.
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> addUser(@RequestBody Map<String, Object> payload) {
		Map<String, String> response = new HashMap<>();

		
		User user = new User();

		user.setEmail(payload.get("email").toString());
		user.setName(payload.get("name").toString());
		user.setUid((user.getEmail() + user.getName()).hashCode());
		user.setAddress(payload.get("address").toString());
		user.setMobileNumber(Integer.parseInt(payload.get("mobileNumber").toString()));
		user.setPassword(payload.get("unhashedPassword").toString().hashCode());
		
		System.out.print(user);
		// TODO ensure the email is not already in the database.
		User existingUser = userService.findByEmail(user.getEmail());
		if (existingUser == null) {
			response.put("success", "true");
			response.put("redirect", "login.html");
			userService.saveUser(user);
		} else {
			response.put("success", "false");
			response.put("redirect", "reg.html");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// UPDATE existing user.
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateUser(@PathVariable("id") long uid, @RequestBody User userUpdate) {
		userService.updateUser(uid, userUpdate);

		return ResponseEntity.status((HttpStatus.OK)).build();
	}

	// CHANGE password.
	@RequestMapping(value = "/password", method = RequestMethod.PUT)
	public ResponseEntity<String> updateUserPassword(@RequestBody Map<String, Object> payload) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}

	// AUTHENTICATION.(sort of acts as a login method as well).
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<HashMap<String, Object>> issueAuthKey(@RequestBody Map<String, Object> payload) {

		String email = payload.get("email").toString();
		long hashedPassword = payload.get("password").toString().hashCode();
		boolean validUser = userService.isPasswordCorrect(email, hashedPassword); // will return false if the user is
																					// not there anyways.
		Map<String, Object> response = new HashMap<>();

		// if the user is there and the password is correct,
		if (validUser) {
			long authKey = email.hashCode() * 12;
			// if (authKey < 0) { authKey = authKey * -1; }
			// we need to store this key in the database for authenticating each proceeding
			// request.
			Session session = new Session();
			session.setUid(userService.getUidOfEmail(email));
			session.setAuthKeyOfUid(authKey);
			session.setRole("user");
			sessionService.saveSession(session);

			response.put("success", true);
			response.put("data", session);
			response.put("redirect", "home.html");

			return new ResponseEntity(response, HttpStatus.OK);
		} else {
			response.put("success", false);
			return new ResponseEntity(response, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/invoke", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, Object>> invokeAuthentication(@RequestHeader("Authentication") long authKey,
			@RequestHeader("ClientId") long uid) {
		sessionService.invokeSessionOfUser(uid);

		Map<String, Object> response = new HashMap<>();
		response.put("success", true);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
