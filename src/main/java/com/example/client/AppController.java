package com.example.client;

import com.example.client.cookies.Cookies;
import com.example.client.event.EventBus;
import com.example.client.event.LoginEvent;
import com.example.client.event.LoginEventHandler;
import com.example.client.model.User;
import com.example.client.presenter.ItemPresenter;
import com.example.client.presenter.LoginPresenter;
import com.example.client.presenter.MainPresenter;
import com.example.client.presenter.Presenter;
import com.example.client.service.ItemService;
import com.example.client.service.ItemServiceAsync;
import com.example.client.service.UserService;
import com.example.client.service.UserServiceAsync;
import com.example.client.view.ItemView;
import com.example.client.view.LoginView;
import com.example.client.view.MainView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class AppController implements Presenter, ValueChangeHandler<String> {

	private EventBus eventBus;
	private HasWidgets container;
	private UserServiceAsync userService;
	private ItemServiceAsync itemService;
	private Cookies cookies;
	
	private LoginPresenter loginPresenter;
	private MainPresenter mainPresenter;
	
	public AppController(EventBus eventBus, Cookies cookies) {
		this.eventBus = eventBus;
		this.cookies = cookies;
		userService = (UserServiceAsync) GWT.create(UserService.class);
		itemService = (ItemServiceAsync) GWT.create(ItemService.class);
		
		loginPresenter = new LoginPresenter(userService, eventBus, cookies, new LoginView());
		ItemPresenter itemPresenter = new ItemPresenter(eventBus, new ItemView());
		mainPresenter =  new MainPresenter(userService, itemService, eventBus, cookies, itemPresenter, new MainView());
		bind();
	}
	
	private void bind() {
		// we listen for all history changes
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
		mainPresenter.go(container);
	}
	
	
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		final String token = event.getValue();
		if(token != null) {
			if(token.equals("login")) {
				loginPresenter.go(container);
			} else if(token.equals("main")) {
				navigateToMain();
			}
		}
	}

	@Override
	public void go(HasWidgets container) {
		this.container = container;
		
		// if no history stack exists, direct to login page
		if("".equals(History.getToken())) {
			History.newItem("login");
		} else {
			navigateToMain();
		}
	}
	
	private void navigateToMain() {
		// check with the server if we are logged in
		final String sid = cookies.getCookie("sid");
		if(sid != null && !(sid.isEmpty())) {
			userService.isLoggedIn(sid, callback);
		}
	}
	
	
	AsyncCallback<User> callback = new AsyncCallback<User>() {
		@Override
		public void onFailure(Throwable t) {
			// re-direct to login; server expired us or has never seen us
			History.newItem("login");
		}

		@Override
		public void onSuccess(User u) {
			// we are logged in
			mainPresenter.go(container);
			eventBus.fireEvent(new LoginEvent(u));
		}
		
	};

}
