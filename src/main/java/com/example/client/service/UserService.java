/**
 * 
 */
package com.example.client.service;

import com.example.client.exception.LoginFailureException;
import com.example.client.model.User;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Service for logging into the application.
 */
@RemoteServiceRelativePath("login.rpc")
public interface UserService extends RemoteService {

	/**
	 * Login to the application
	 * <p>
	 * @param user the user object with {@link User#getUsername()} and {@link User#getPassword()} filled in.
	 * @return the sessionId that is necessary for all future GWT-RPC requests
	 * @throws LoginFailureException if login fails
	 */
	String login(User user) throws LoginFailureException;
	
	/**
	 * Logout of the application
	 * @param sessionId the user's sessionid
	 */
	void logout(String sessionId);
}
