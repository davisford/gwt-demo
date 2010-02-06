/**
 * 
 */
package com.example.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author davisford
 *
 */
public class LoginFailureException extends Exception implements IsSerializable {

	// serial uid
	private static final long serialVersionUID = 1L;
	
	private static final String DEFAULT_MSG = "The email or password you entered is incorrect.";

	/**
	 * Constructor
	 */
	public LoginFailureException() {
		super(DEFAULT_MSG);
	}

	/**
	 * @param msg
	 */
	public LoginFailureException(String msg) {
		super(msg);
	}

	/**
	 * @param t
	 */
	public LoginFailureException(Throwable t) {
		super(t);
	}

	/**
	 * @param msg
	 * @param t
	 */
	public LoginFailureException(String msg, Throwable t) {
		super(msg, t);
	}

}
