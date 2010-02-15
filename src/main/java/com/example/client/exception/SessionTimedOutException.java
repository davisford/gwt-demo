package com.example.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SessionTimedOutException extends Exception implements IsSerializable  {

	// serial uid
	private static final long serialVersionUID = 1L;

	private static final String DEFAULT_MSG = "The session has timed out.  You will need to login again.";

	/**
	 * Constructor
	 */
	public SessionTimedOutException() {
		super(DEFAULT_MSG);
	}

	/**
	 * @param msg
	 */
	public SessionTimedOutException(String msg) {
		super(msg);
	}

	/**
	 * @param t
	 */
	public SessionTimedOutException(Throwable t) {
		super(t);
	}

	/**
	 * @param msg
	 * @param t
	 */
	public SessionTimedOutException(String msg, Throwable t) {
		super(msg, t);
	}
}
