/**
 * 
 */
package com.example.client;

import com.example.client.cookies.Cookies;
import com.example.client.cookies.DefaultCookies;
import com.example.client.event.DefaultEventBus;
import com.example.client.event.EventBus;
import com.google.gwt.core.client.EntryPoint;
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
		// create the global event bus
		EventBus eventBus = new DefaultEventBus();
		// create the cookies wrapper
		Cookies cookies = new DefaultCookies();
		AppController controller = new AppController(eventBus, cookies);
		controller.go(RootLayoutPanel.get());
	}

}
