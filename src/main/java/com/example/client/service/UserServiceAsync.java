/**
 * 
 */
package com.example.client.service;

import com.example.client.model.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The asynchronous version of {@link UserService}
 */
public interface UserServiceAsync {

	/**
	 * @see {@link UserService#login(User)}
	 */
	void login(User user, AsyncCallback<String> callback);

	/**
	 * @see {@link UserService#logout(String)}
	 */
	void logout(String sessionId, AsyncCallback<Void> callback);

	/**
	 * @see {@link UserService#isLoggedIn(String)}
	 */
	void isLoggedIn(String sessionId, AsyncCallback<User> callback);
	
}
