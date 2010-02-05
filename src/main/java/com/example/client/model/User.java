/**
 * 
 */
package com.example.client.model;

import java.io.Serializable;

/**
 * The model class that represents a user of the system
 */
public class User implements Serializable {
	
	// serial uid
	private static final long serialVersionUID = 1L;
	
	private String username;
	
	private String password;
	
	private String sessionId;

	/**
	 * Must have default no-arg constructor
	 */
	public User() {}
	
	/**
	 * Constructor
	 * <p>
	 * @param username
	 * @param password
	 */
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Indicates if the object is valid for submission
	 * to the server.
	 * @return <tt>true</tt> if valid; 
	 */
	public boolean isValid() {
		return 
			(username != null) && !(username.isEmpty()) &&
			(password != null) && !(password.isEmpty());
	}

	public final String getUsername() {
		return username;
	}

	public final void setUsername(String username) {
		this.username = username;
	}

	public final String getPassword() {
		return password;
	}

	public final void setPassword(String password) {
		this.password = password;
	}

	public final String getSessionId() {
		return sessionId;
	}

	public final void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
