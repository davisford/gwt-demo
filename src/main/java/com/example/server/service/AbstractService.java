package com.example.server.service;

import javax.servlet.http.HttpSession;

/**
 * Allows subclasses to access {@link HttpSession} if they need it.
 */
public class AbstractService {
	
	protected HttpSession httpSession;

	/**
	 * Set the {@link HttpSession} object so we can add things to the 
	 * session in service implementations.
	 * @param session
	 */
	public void setHttpSession(HttpSession session) {
		this.httpSession = session;
	}

}
