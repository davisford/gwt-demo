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
import com.google.gwt.user.client.ui.Grid;
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
	Label nameLabel;
	
	@UiField
	Hyperlink logoutLink;
	
	@UiField
	Grid grid;


	public MainView() {
		initWidget(uiBinder.createAndBindUi(this));
	}


	@Override
	public Widget asWidget() {
		return this;
	}


	@Override
	public void setNameText(String text) {
		nameLabel.setText(text);
	}


	@Override
	public HasClickHandlers logoutLink() {
		return logoutLink;
	}

}
