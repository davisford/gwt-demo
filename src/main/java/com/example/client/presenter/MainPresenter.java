/**
 * 
 */
package com.example.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

/**
 * Presenter for the main view
 */
public class MainPresenter implements Presenter {
	
	/**
	 * Display interface for the main view
	 */
	public interface Display extends WidgetDisplay {
		
	}
	
	private Display display;
	private HandlerManager eventBus;
	
	/**
	 * Constructor
	 * @param eventBus
	 * @param display
	 */
	public MainPresenter(HandlerManager eventBus, Display display) {
		this.display = display;
		this.eventBus = eventBus;
		bind();
	}
	
	private void bind() {
		
	}

	/* (non-Javadoc)
	 * @see com.example.client.presenter.Presenter#go(com.google.gwt.user.client.ui.HasWidgets)
	 */
	@Override
	public void go(HasWidgets container) {
		container.clear();
	    container.add(display.asWidget());
	}

}
