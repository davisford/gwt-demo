/**
 * 
 */
package com.example.client.view;

import com.example.client.presenter.MainPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Main application view
 */
public class MainView extends Composite implements MainPresenter.Display {

	private static MainViewUiBinder uiBinder = GWT.create(MainViewUiBinder.class);

	interface MainViewUiBinder extends UiBinder<Widget, MainView> {
	}
	
	@UiField
	Label welcomeLabel;
	
	@UiField
	Hyperlink logoutLink;


	public MainView() {
		initWidget(uiBinder.createAndBindUi(this));
	}


	@Override
	public Widget asWidget() {
		return this;
	}


	@Override
	public void setWelcomeText(String text) {
		welcomeLabel.setText(text);
	}


	@Override
	public HasClickHandlers logoutLink() {
		return logoutLink;
	}

}
