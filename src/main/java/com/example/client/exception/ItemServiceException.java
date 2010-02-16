/**
 * 
 */
package com.example.client.exception;

import com.example.client.service.ItemService;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Thrown from the server if anything goes wrong with the {@link ItemService}
 */
public class ItemServiceException extends Exception implements IsSerializable {
	
	// serial uid
	private static final long serialVersionUID = 1L;
	
	private static final String DEFAULT_MSG = "Oops, we're having problems with the server.  Try again later.";

	/**
	 * 
	 */
	public ItemServiceException() {
		super(DEFAULT_MSG);
	}

	/**
	 * @param message
	 */
	public ItemServiceException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ItemServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ItemServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
