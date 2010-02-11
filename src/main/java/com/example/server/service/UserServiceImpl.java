/**
 * 
 */
package com.example.server.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.example.client.exception.LoginFailureException;
import com.example.client.model.User;
import com.example.client.service.UserService;
import com.example.server.dao.UserDao;
import com.example.server.security.BCrypt;

/**
 * Implementation of {@link UserService}
 */
public class UserServiceImpl extends AbstractService implements UserService {
	
	private UserDao userDao;
	
	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
	
	private Map<String, User> sessionMap = new HashMap<String, User>();

	/* (non-Javadoc)
	 * @see com.example.client.service.LoginService#login(com.example.client.model.User)
	 */
	@Override
	public String login(User user) throws LoginFailureException {
		
		try {
			if(user == null || user.isValid() == false) {
				LOGGER.error("Bogus user data was received "+user);
				throw new LoginFailureException();
			}
			
			final User found = userDao.findByUserName(user.getUsername());
			if(found == null) {
				throw new LoginFailureException("Sorry, we couldn't locate you in our records.");
			}
			
			// validate the password
			if(BCrypt.checkpw(user.getPassword(), found.getPassword())) {
				final String id = httpSession.getId();
				sessionMap.put(id, found);
				return id;
			} else {
				throw new LoginFailureException();
			}
		} catch(Exception e) {
			LOGGER.error(e);
			throw new LoginFailureException();
		}
	}
	
	public void setUserDao(UserDao dao) {
		this.userDao = dao;
	}

	@Override
	public void logout(String sessionId) {
		if(httpSession.getId().equals(sessionId)) {
			LOGGER.debug("session ids are the same " + sessionId + " invalidating sesison now ");
			sessionMap.remove(sessionId);
			httpSession.invalidate();
		} else {
			throw new RuntimeException("Session Ids are not equal: "+sessionId + " != httpsession id: "+httpSession.getId());
		}
	}

	@Override
	public User isLoggedIn(String sessionId) throws LoginFailureException {
		if(sessionMap.containsKey(sessionId)) {
			return sessionMap.get(sessionId);
		} else {
			throw new LoginFailureException();
		}
	}

}
