/**
 * 
 */
package com.example.client.presenter;

import java.util.Date;
import java.util.List;

import com.example.client.cookies.Cookies;
import com.example.client.event.EventBus;
import com.example.client.event.LoginEvent;
import com.example.client.model.User;
import com.example.client.service.UserServiceAsync;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

/**
 * {@link Presenter} implementation for login
 */
public class LoginPresenter implements Presenter {
	
	public interface Display extends WidgetDisplay {
		/**
		 * Get the {@link User} that we'll attempt to login
		 * @return
		 */
		User getUser();
		
		/**
		 * Get the login button from the display
		 * @return
		 */
		HasClickHandlers loginButton();
		
		/**
		 * Get any {@link HasKeyDownHandlers} from the display
		 * that will also trigger a login on ENTER.
		 * @return
		 */
		List<HasKeyDownHandlers> keyDownHandlers();
		
		/**
		 * Tell the display to show an error message.
		 * @param msg message to display; may be <tt>null</tt> if you want to
		 * clear the error message.
		 */
		void setErrorMsg(String msg);
	}
	
	private UserServiceAsync loginService;
	private EventBus eventBus;
	private Cookies cookies;
	private Display display;
	
	/**
	 * Constructor
	 * <p>
	 * @param loginService rpc service used to login
	 * @param eventBus application event bus
	 * @param display the display for this presenter
	 */
	public LoginPresenter(UserServiceAsync loginService, EventBus eventBus, Cookies cookies, Display display) {
		this.loginService = loginService;
		this.eventBus = eventBus;
		this.cookies = cookies;
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
		display.setErrorMsg(null);
	    user = display.getUser();
		if(user.isValid()) {
			loginService.login(user, callback);
		} else {
			display.setErrorMsg("Please enter a username and password");
		}
	}
	
	/**
	 * {@link ClickHandler} that runs when the user clicks login
	 */
	ClickHandler loginClickHandler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent evt) {
			doLogin();
		}
	};
	
	/**
	 * {@link KeyDownHandler} that runs when the user presses ENTER
	 */
	KeyDownHandler keyDownHandler = new KeyDownHandler() {
		@Override
		public void onKeyDown(KeyDownEvent evt) {
			display.setErrorMsg(null);
			if(KeyCodes.KEY_ENTER == evt.getNativeKeyCode()) { 
				doLogin();
			}
		}		
	};
	
	/**
	 * {@link AsyncCallback} for user service
	 */
	AsyncCallback<String> callback = new AsyncCallback<String>() {
		@Override
		public void onFailure(Throwable t) {
			display.setErrorMsg(t.getMessage());
		}

		@Override
		public void onSuccess(String sessionId) {
			// set a client side cookie for two weeks
			final long DURATION = 1000 * 60 * 60 * 24 * 14;
			final Date expires = new Date(System.currentTimeMillis() + DURATION);
			cookies.setCookie("sid", sessionId, expires, null, "/", false);
			
			// fire event
			eventBus.fireEvent(new LoginEvent(user));
		}
		
	};

}
