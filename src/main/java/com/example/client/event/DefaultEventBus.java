/**
 * 
 */
package com.example.client.event;

import com.google.gwt.event.shared.HandlerManager;

/**
 * Implements {@link EventBus} by wrapping {@link HandlerManager}.
 */
public class DefaultEventBus extends HandlerManager implements EventBus {
	
	public DefaultEventBus() {
		super(null);
	}
}
