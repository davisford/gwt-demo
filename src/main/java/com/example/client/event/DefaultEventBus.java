/**
 * 
 */
package com.example.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * Implements {@link EventBus} by wrapping {@link HandlerManager}.
 */
public class DefaultEventBus implements EventBus {
	
	private HandlerManager handlerManager;
	
	public DefaultEventBus() {
		handlerManager = new HandlerManager(null);
	}

	/* (non-Javadoc)
	 * @see com.example.client.event.EventBus#addHandler(com.google.gwt.event.shared.GwtEvent.Type, com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	public <H extends EventHandler> HandlerRegistration addHandler(
			Type<H> type, H handler) {
		return handlerManager.addHandler(type, handler);
	}

	/* (non-Javadoc)
	 * @see com.example.client.event.EventBus#fireEvent(com.google.gwt.event.shared.GwtEvent)
	 */
	@Override
	public void fireEvent(GwtEvent<?> event) {
		handlerManager.fireEvent(event);
	}

	/* (non-Javadoc)
	 * @see com.example.client.event.EventBus#getHandler(com.google.gwt.event.shared.GwtEvent.Type, int)
	 */
	@Override
	public <H extends EventHandler> H getHandler(Type<H> type, int index) {
		return handlerManager.getHandler(type, index);
	}

	/* (non-Javadoc)
	 * @see com.example.client.event.EventBus#getHandlerCount(com.google.gwt.event.shared.GwtEvent.Type)
	 */
	@Override
	public int getHandlerCount(Type<?> type) {
		return handlerManager.getHandlerCount(type);
	}

	/* (non-Javadoc)
	 * @see com.example.client.event.EventBus#isEventHandled(com.google.gwt.event.shared.GwtEvent.Type)
	 */
	@Override
	public boolean isEventHandled(Type<?> e) {
		return handlerManager.isEventHandled(e);
	}

	/* (non-Javadoc)
	 * @see com.example.client.event.EventBus#removeHandler(com.google.gwt.event.shared.GwtEvent.Type, com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	public <H extends EventHandler> void removeHandler(Type<H> type, H handler) {
		handlerManager.removeHandler(type, handler);
	}

}
