/**
 * 
 */
package com.example.client.event;

import com.example.client.model.User;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author davisford
 *
 */
public class LoginEvent extends GwtEvent<LoginEventHandler> {

	public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();
	
	private User _user;
	
	public LoginEvent(User user) {
		_user = user;
	}
	
	public User getUser() {
		return _user;
	}
	
	@Override
	protected void dispatch(LoginEventHandler handler) {
		handler.onLogin(this);
	}

	@Override
	public Type<LoginEventHandler> getAssociatedType() {
		return TYPE;
	}

}
