/**
 * 
 */
package com.example.client.presenter;

import com.example.client.event.EventBus;
import com.example.client.event.LoginEvent;
import com.example.client.event.LoginEventHandler;
import com.example.client.service.UserServiceAsync;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

/**
 * Presenter for the main view
 */
public class MainPresenter implements Presenter {
	
	/**
	 * Display interface for the main view
	 */
	public interface Display extends WidgetDisplay {
		/**
		 * Set the welcome text after a user logs in
		 * @param text
		 */
		void setWelcomeText(String text);
		
		/**
		 * So we can assign a click handler
		 * @return
		 */
		HasClickHandlers logoutLink();
	}
	
	private Display display;
	private EventBus eventBus;
	
	private UserServiceAsync userService;
	
	/**
	 * Constructor
	 * @param eventBus
	 * @param display
	 */
	public MainPresenter(UserServiceAsync userService, EventBus eventBus, Display display) {
		this.userService = userService;
		this.display = display;
		this.eventBus = eventBus;
		bind();
	}
	
	private void bind() {
		// let me know when login happens so i can update the welcome text
		eventBus.addHandler(LoginEvent.TYPE, loginHandler);
		// add handler when user clicks logout link
		display.logoutLink().addClickHandler(logoutClickHandler);
	}

	/* (non-Javadoc)
	 * @see com.example.client.presenter.Presenter#go(com.google.gwt.user.client.ui.HasWidgets)
	 */
	@Override
	public void go(HasWidgets container) {
		container.clear();
	    container.add(display.asWidget());
	}
	
	LoginEventHandler loginHandler = new LoginEventHandler() {
		@Override
		public void onLogin(LoginEvent loginEvent) {
			display.setWelcomeText("Welcome back, "+loginEvent.getUser().getUsername());
		}
	};
	
	ClickHandler logoutClickHandler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent evt) {
			userService.logout(Cookies.getCookie("sid"), new AsyncCallback<Void>() {
				@Override
				public void onFailure(Throwable t) {
					// no-op
				}

				@Override
				public void onSuccess(Void v) {
					// no-op
				}
				
			});
		}
		
	};

}
