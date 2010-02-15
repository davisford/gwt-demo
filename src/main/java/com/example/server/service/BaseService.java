package com.example.server.service;

import javax.servlet.http.HttpSession;

public interface BaseService {

	/**
	 * Set the {@link HttpSession} object so we can add things to the 
	 * session in service implementations.
	 * @param session
	 */
	 void setHttpSession(HttpSession session);

	/**
	 * Checks whether the sessionId is valid.  If it is not valid, the
	 * service should respond with {@link com.example.client.exception.SessionTimedOutException}
	 * 
	 * @param sessionId the sesisonId to check
	 * @return <tt>true</tt> if the sessionId is valid.
	 */
	boolean isSessionValid(String sessionId);

}