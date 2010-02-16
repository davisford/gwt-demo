package com.example.client.view;

import java.util.ArrayList;
import java.util.List;

import com.example.client.model.User;
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

	interface LoginUiBinder extends UiBinder<Widget, LoginView> {	}

	@UiField DecoratorPanel loginPanel;
	
	@UiField TextBox usernameBox;
	
	@UiField PasswordTextBox passwordBox;
	
	@UiField Button loginButton;
	
	@UiField Label errorLabel;

	/**
	 * Constructor
	 */
	public LoginView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/*
	 * (non-Javadoc)
	 * @see com.example.client.presenter.WidgetDisplay#asWidget()
	 */
	@Override
	public Widget asWidget() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.example.client.presenter.LoginPresenter.Display#getUser()
	 */
	@Override
	public User getUser() {
		return new User(usernameBox.getText(), passwordBox.getText());
	}

	/*
	 * (non-Javadoc)
	 * @see com.example.client.presenter.LoginPresenter.Display#loginButton()
	 */
	@Override
	public HasClickHandlers loginButton() {
		return loginButton;
	}

	/*
	 * (non-Javadoc)
	 * @see com.example.client.presenter.LoginPresenter.Display#setErrorMsg(java.lang.String)
	 */
	@Override
	public void setErrorMsg(String msg) {
		errorLabel.setText(msg);
	}

	/*
	 * (non-Javadoc)
	 * @see com.example.client.presenter.LoginPresenter.Display#keyDownHandlers()
	 */
	@Override
	public List<HasKeyDownHandlers> keyDownHandlers() {
		final List<HasKeyDownHandlers> list = new ArrayList<HasKeyDownHandlers>();
		list.add(usernameBox);
		list.add(passwordBox);
		return list;
	}
}
