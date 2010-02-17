package com.example.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface SessionTimedOutEventHandler extends EventHandler {
	
	void onTimeOut(SessionTimedOutEvent evt);

}
