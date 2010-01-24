package com.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Application implements EntryPoint {
	
	private final Label header = new Label("I'm the header");
	private final Label navigation = new Label("I'm the navigation");
	private final Label content = new Label("I'm the content");
	private final Label footer = new Label("I'm the footer");

	@Override
	public void onModuleLoad() {
		RootPanel.get("header").add(header);
		RootPanel.get("navigation").add(navigation);
		RootPanel.get("content").add(content);
		RootPanel.get("footer").add(footer);
		
		Label l = new Label();
		l.setText("BLAH BLAH BLAH BALH");
		RootPanel.get().add(l);
	}
	
	
}