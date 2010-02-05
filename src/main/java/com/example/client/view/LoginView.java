/**
 * 
 */
package com.example.client.view;

import java.util.ArrayList;
import java.util.List;

import com.example.client.presenter.LoginPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * View class for {@link LoginPresenter}
 */
public class LoginView extends Composite implements LoginPresenter.Display {

	private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);

	interface LoginUiBinder extends UiBinder<Widget, LoginView> {
	}

	@UiField
	DecoratorPanel loginPanel;
	
	@UiField
	TextBox usernameBox;
	
	@UiField
	PasswordTextBox passwordBox;
	
	@UiField
	Button loginButton;
	
	@UiField
	Label errorLabel;

	public LoginView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public String password() {
		return passwordBox.getText();
	}

	@Override
	public String username() {
		return usernameBox.getText();
	}

	@Override
	public HasClickHandlers loginButton() {
		return loginButton;
	}

	@Override
	public void toggleError(boolean val, String msg) {
		if(val) {
			errorLabel.addStyleName("errorBorder");
			errorLabel.setText(msg);
		} else {
			errorLabel.removeStyleName("errorBorder");
			errorLabel.setText(msg);
		}
	}

	@Override
	public List<HasKeyDownHandlers> keyDownHandlers() {
		final List<HasKeyDownHandlers> list = new ArrayList<HasKeyDownHandlers>();
		list.add(usernameBox);
		list.add(passwordBox);
		return list;
	}

}
