/**
 * 
 */
package com.example.client.service;

import com.example.client.model.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The asynchronous version of {@link LoginService}
 */
public interface LoginServiceAsync {

	/**
	 * @see {@link LoginService#login(User)}
	 */
	void login(User user, AsyncCallback<User> callback);
	
}
