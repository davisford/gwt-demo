/**
 * 
 */
package com.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * @author davisford
 *
 */
public class Application implements EntryPoint {

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	@Override
	public void onModuleLoad() {
		HandlerManager eventBus = new HandlerManager(null);
		AppController controller = new AppController(eventBus);
		controller.go(RootLayoutPanel.get());
	}

}
