/**
 * 
 */
package com.example.client.presenter;

import java.util.Date;
import java.util.List;

import com.example.client.event.LoginEvent;
import com.example.client.model.User;
import com.example.client.service.LoginServiceAsync;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

/**
 * {@link Presenter} implementation for login
 */
public class LoginPresenter implements Presenter {
	
	public interface Display extends WidgetDisplay {
		/**
		 * Get the username from the display
		 * @return
		 */
		String username();
		/**
		 * Get the password from the display
		 * @return
		 */
		String password();
		/**
		 * Get the login button from the display
		 * @return
		 */
		HasClickHandlers loginButton();
		
		List<HasKeyDownHandlers> keyDownHandlers();
		
		/**
		 * Tell the display to toggle error on/off
		 * @param val <tt>true</tt> means show error; <tt>false</tt> means remove
		 * @param msg message to display; may be <tt>null</tt>
		 */
		void toggleError(boolean val, String msg);
	}
	
	private LoginServiceAsync loginService;
	private HandlerManager eventBus;
	private Display display;
	
	/**
	 * Constructor
	 * <p>
	 * @param loginService rpc service used to login
	 * @param eventBus application event bus
	 * @param display the display for this presenter
	 */
	public LoginPresenter(LoginServiceAsync loginService, HandlerManager eventBus, Display display) {
		this.loginService = loginService;
		this.eventBus = eventBus;
		this.display = display;
		bind();
	}

	/**
	 * Bind ourself to the display controls
	 */
	private void bind() {
		display.loginButton().addClickHandler(loginClickHandler);
		for(HasKeyDownHandlers h : display.keyDownHandlers()) {
			h.addKeyDownHandler(keyDownHandler);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.example.client.presenter.Presenter#go(com.google.gwt.user.client.ui.HasWidgets)
	 */
	@Override
	public void go(HasWidgets container) {
		container.clear();
	    container.add(display.asWidget());
	}
	
	private User user;
	
	private void doLogin() {
		// clear all previous errors
		display.toggleError(false, null);
	    user = new User(display.username(), display.password());
		if(user.isValid()) {
			loginService.login(user, callback);
		} else {
			display.toggleError(true, "Please enter a username and password");
		}
	}
	
	ClickHandler loginClickHandler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent evt) {
			doLogin();
		}
	};
	
	KeyDownHandler keyDownHandler = new KeyDownHandler() {
		@Override
		public void onKeyDown(KeyDownEvent evt) {
			display.toggleError(false, null);
			if(KeyCodes.KEY_ENTER == evt.getNativeKeyCode()) { 
				doLogin();
			}
		}		
	};
	
	AsyncCallback<String> callback = new AsyncCallback<String>() {
		@Override
		public void onFailure(Throwable t) {
			display.toggleError(true, t.getMessage());
		}

		@Override
		public void onSuccess(String sessionId) {
			final long DURATION = 1000 * 60 * 60 * 24 * 14;
			final Date expires = new Date(System.currentTimeMillis() + DURATION);
			Cookies.setCookie("sid", sessionId, expires, null, "/", false);
			
			// fire event
			eventBus.fireEvent(new LoginEvent(user));
		}
		
	};

}
