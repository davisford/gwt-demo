/**
 * 
 */
package com.example.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * We extract {@link com.google.gwt.event.shared.HandlerManager} methods out
 * into an interface so that we can mock it.  
 */
public interface EventBus {

	/**
	 * Add an event handler of type H to the bus
	 * @param <H> the type of the handler
	 * @param type the event type associated with this handler
	 * @param handler the handler
	 * @return the handler registration; this can be stored in order to remove the handler later
	 */
	<H extends EventHandler> HandlerRegistration addHandler(GwtEvent.Type<H> type, H handler);
	
	/**
	 * Fires the given event to the handlers listening to the event's type.  Note, any subclass
	 * should be very careful about overriding this method, as adds/removes of handlers will not
	 * be safe except within this implementation.
	 * 
	 * @param event the event to dispatch to any listening handlers for that type
	 */
	void fireEvent(GwtEvent<?> event);
	
	/**
	 * Gets the handler at the given index.
	 * @param <H> the event handler type
	 * @param type the handler's event type
	 * @param index the index
	 * @return the given handler
	 */
	<H extends EventHandler> H getHandler(GwtEvent.Type<H> type, int index);
	
	/**
	 * Get the number of handlers listening to the event type
	 * @param type the event type
	 * @return the number of registered handlers
	 */
	int getHandlerCount(GwtEvent.Type<?> type);
	
	/**
	 * Does this event bus handle the given event type?
	 * @param e the event type
	 * @return whether the given event type is handled by one or more handlers
	 */
	boolean isEventHandled(GwtEvent.Type<?> e);
	
	/**
	 * Removes the given handler from the specified event type.
	 * @param <H> the handler type
	 * @param type the event type
	 * @param handler the handler to remove
	 */
	<H extends EventHandler> void removeHandler(GwtEvent.Type<H> type, H handler);
}
