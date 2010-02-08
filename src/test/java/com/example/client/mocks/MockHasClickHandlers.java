/**
 * 
 */
package com.example.client.mocks;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * Mock implementation of {@link HasClickHandlers}
 */
public class MockHasClickHandlers implements HasClickHandlers {

	public int addClickHandlerCnt;
	
	public int fireEventCnt;
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.HasClickHandlers#addClickHandler(com.google.gwt.event.dom.client.ClickHandler)
	 */
	@Override
	public HandlerRegistration addClickHandler(ClickHandler arg0) {
		addClickHandlerCnt++;
		return null;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.HasHandlers#fireEvent(com.google.gwt.event.shared.GwtEvent)
	 */
	@Override
	public void fireEvent(GwtEvent<?> arg0) {
		fireEventCnt++;
	}

}
