package com.example.client;

import com.example.client.event.LoginEvent;
import com.example.client.event.LoginEventHandler;
import com.example.client.presenter.LoginPresenter;
import com.example.client.presenter.MainPresenter;
import com.example.client.presenter.Presenter;
import com.example.client.service.UserService;
import com.example.client.service.UserServiceAsync;
import com.example.client.view.LoginView;
import com.example.client.view.MainView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

public class AppController implements Presenter, ValueChangeHandler<String> {

	private HandlerManager eventBus;
	private HasWidgets container;
	private UserServiceAsync userService;
	
	public AppController(HandlerManager eventBus) {
		this.eventBus = eventBus;
		userService = (UserServiceAsync) GWT.create(UserService.class);
		bind();
	}
	
	private void bind() {
		History.addValueChangeHandler(this);
		
		eventBus.addHandler(LoginEvent.TYPE, 
			new LoginEventHandler() {
				@Override
				public void onLogin(LoginEvent loginEvent) {
					doLogin();
				}
		});
	}
	
	private void doLogin() {
		History.newItem("main", false);
		History.fireCurrentHistoryState();
	}
	
	
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		final String token = event.getValue();
		if(token != null) {
			Presenter presenter = null;
			
			if(token.equals("login")) {
				presenter = new LoginPresenter(userService, eventBus, new LoginView());
			} else if(token.equals("main")) {
				presenter = new MainPresenter(userService, eventBus, new MainView());
			}
			
			if(presenter != null) {
				presenter.go(container);
			}
		}
	}

	@Override
	public void go(HasWidgets container) {
		this.container = container;
		
		if("".equals(History.getToken())) {
			History.newItem("login");
		} else {
			History.fireCurrentHistoryState();
		}
	}

}
