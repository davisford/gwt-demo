package com.example.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Fired when a session has timed out b/c a service call generated a
 * {@link SessionTimedOutException}
 */
public class SessionTimedOutEvent extends GwtEvent<SessionTimedOutEventHandler> {

	public static Type<SessionTimedOutEventHandler> TYPE = new Type<SessionTimedOutEventHandler>();
	
	@Override
	protected void dispatch(SessionTimedOutEventHandler handler) {
		handler.onTimeOut(this);
	}

	@Override
	public Type<SessionTimedOutEventHandler> getAssociatedType() {
		return TYPE;
	}

}
