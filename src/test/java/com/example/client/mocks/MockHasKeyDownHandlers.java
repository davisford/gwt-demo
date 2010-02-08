/**
 * 
 */
package com.example.client.mocks;

import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * Mock implementation of {@link HasKeyDownHandlers}
 */
public class MockHasKeyDownHandlers implements HasKeyDownHandlers {

	public int addHandlerCount;
	public int fireEventCount;
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.HasKeyDownHandlers#addKeyDownHandler(com.google.gwt.event.dom.client.KeyDownHandler)
	 */
	@Override
	public HandlerRegistration addKeyDownHandler(KeyDownHandler arg0) {
		addHandlerCount++;
		return null;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.HasHandlers#fireEvent(com.google.gwt.event.shared.GwtEvent)
	 */
	@Override
	public void fireEvent(GwtEvent<?> arg0) {
		fireEventCount++;
	}

}
