package com.example.client;

import com.example.client.cookies.Cookies;
import com.example.client.event.EventBus;
import com.example.client.event.LoginEvent;
import com.example.client.event.LoginEventHandler;
import com.example.client.event.SessionTimedOutEvent;
import com.example.client.event.SessionTimedOutEventHandler;
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

/**
 * This is a {@link Presenter} without a view.  It is responsible for managing
 * history and navigation events.  It also is responsible for bootstrapping the
 * other major components in the application.
 */
public class AppController implements Presenter, ValueChangeHandler<String> {

	private EventBus eventBus;
	private HasWidgets container;
	private UserServiceAsync userService;
	private ItemServiceAsync itemService;
	private Cookies cookies;
	
	private LoginPresenter loginPresenter;
	private MainPresenter mainPresenter;
	
	/**
	 * Constructor
	 * <p>
	 * @param eventBus the application's {@link EventBus}
	 * @param cookies the application's cookie manager
	 */
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
		
		// we listen for login events to navigate to main
		eventBus.addHandler(LoginEvent.TYPE, 
			new LoginEventHandler() {
				@Override
				public void onLogin(LoginEvent loginEvent) {
					// if a login event occurs, we navigate
					doMain();
				}
		});
		
		// we listen for session time out events to navigate to login
		eventBus.addHandler(SessionTimedOutEvent.TYPE, 
				new SessionTimedOutEventHandler() {
					@Override
					public void onTimeOut(SessionTimedOutEvent evt) {
						// navigate back to login
						doLogin();
					}
		});
	}
	
	/**
	 * Switch the view if login was successful
	 */
	private void doMain() {
		History.newItem("main", false);
		mainPresenter.go(container);
	}
	
	private void doLogin() {
		History.newItem("login", false);
		loginPresenter.go(container);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.event.logical.shared.ValueChangeHandler#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)
	 */
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

	/**
	 * Our initial view
	 */
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
	
	/**
	 * If the user loads the application after having logged in,
	 * we check if the session is still valid on the server 
	 * before we just re-direct to the main view.
	 */
	private void navigateToMain() {
		// check with the server if we are logged in
		final String sid = cookies.getCookie("sid");
		if(sid != null && !(sid.isEmpty())) {
			userService.isLoggedIn(sid, callback);
		}
	}
	
	/**
	 * The callback from the server when we ask it if the session is logged in
	 */
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
			// tell the world
			eventBus.fireEvent(new LoginEvent(u));
		}
	};

}
