package com.example.server.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.example.client.model.User;

/**
 * Allows subclasses to access {@link HttpSession} if they need it.
 */
public class AbstractService implements BaseService {
	
	protected HttpSession httpSession;
	
	protected static final Map<String, User> sessionMap = new HashMap<String, User>();

	/* (non-Javadoc)
	 * @see com.example.server.service.BaseService#setHttpSession(javax.servlet.http.HttpSession)
	 */
	public void setHttpSession(HttpSession session) {
		this.httpSession = session;
	}
	
	/* (non-Javadoc)
	 * @see com.example.server.service.BaseService#isSessionValid(java.lang.String)
	 */
	public boolean isSessionValid(String sessionId) {
		return sessionMap.containsKey(sessionId);
	}

}
