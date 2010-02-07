/**
 * 
 */
package com.example.server.service;

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
				return httpSession.getId();
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
			httpSession.invalidate();
		} else {
			throw new RuntimeException("Session Ids are not equal: "+sessionId + " != httpsession id: "+httpSession.getId());
		}
	}

}
