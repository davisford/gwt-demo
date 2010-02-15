/**
 * 
 */
package com.example.client.presenter;

import java.util.ArrayList;

import com.example.client.cookies.Cookies;
import com.example.client.event.EventBus;
import com.example.client.event.LoginEvent;
import com.example.client.event.LoginEventHandler;
import com.example.client.exception.SessionTimedOutException;
import com.example.client.model.Item;
import com.example.client.service.ItemServiceAsync;
import com.example.client.service.UserServiceAsync;
import com.example.client.view.MainView.SelectedItemListener;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
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
		void setNameText(String text);
		
		/**
		 * So we can assign a click handler
		 * @return
		 */
		HasClickHandlers logoutLink();
		
		/**
		 * So we can assign a click handler to refresh widget
		 * @return
		 */
		HasClickHandlers refresh();
		
		/**
		 * So we can assign a click handler to delete widget
		 * @return
		 */
		HasClickHandlers delete();
		
		
		/**
		 * So we can assign a click handler to the new widget
		 * @return
		 */
		HasClickHandlers newItemClick();
		
		/**
		 * Set the list of items fetched from the server on the view
		 * @param items
		 */
		void setItems(ArrayList<Item> items);
		
		/**
		 * Get the list of selected items from the display
		 * @return
		 */
		ArrayList<Item> getSelectedItems();
		
		/**
		 * Tell the display to show an error message.
		 * @param msg message to display; may be <tt>null</tt> if you want to
		 * clear the error message.
		 */
		void setErrorMsg(String msg);
		
		/**
		 * Set the listener to callback when an item is selected
		 * @param listener
		 */
		void setSelectedItemListener(SelectedItemListener listener);
	}
	
	private Display display;
	private EventBus eventBus;
	private Cookies cookies;
	
	private UserServiceAsync userService;
	private ItemServiceAsync itemService;
	
	/**
	 * Constructor
	 * @param eventBus
	 * @param display
	 */
	public MainPresenter(UserServiceAsync userService, ItemServiceAsync itemService, EventBus eventBus, Cookies cookies, Display display) {
		this.userService = userService;
		this.itemService = itemService;
		this.cookies = cookies;
		this.display = display;
		this.eventBus = eventBus;
		bind();
	}
	
	private void bind() {
		// let me know when login happens so i can update the welcome text and fetch the items
		eventBus.addHandler(LoginEvent.TYPE, loginHandler);
		
		// add handler when user clicks logout link
		display.logoutLink().addClickHandler(logoutClickHandler);
		
		// add handler when user clicks refresh button
		display.refresh().addClickHandler(refreshClickHandler);
		
		// add handler when user clicks delete button
		display.delete().addClickHandler(deleteClickHandler);
		
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
			display.setNameText(loginEvent.getUser().getUsername());
			
			// fetch the list of items
			itemService.findAll(cookies.getCookie("sid"), findCallback);
		}
	};
	
	ClickHandler logoutClickHandler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent evt) {
			// logout on the server
			userService.logout(cookies.getCookie("sid"), new AsyncCallback<Void>() {
				@Override
				public void onFailure(Throwable t) {
					// no-op
				}

				@Override
				public void onSuccess(Void v) {
					// no-op
				}
				
			});
			// clear the client side cookie
			cookies.removeCookies("sid");
		}
		
	};
	
	ClickHandler refreshClickHandler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent evt) {
			itemService.findAll(cookies.getCookie("sid"), findCallback);
		}
		
	};
	
	AsyncCallback<ArrayList<Item>> findCallback = new AsyncCallback<ArrayList<Item>>() {
		@Override
		public void onFailure(Throwable t) {
			handleThrowable(t);
		}

		@Override
		public void onSuccess(ArrayList<Item> list) {
			display.setItems(list);
		}
		
	};
	
	ClickHandler deleteClickHandler = new ClickHandler() {

		@Override
		public void onClick(ClickEvent arg0) {
			final ArrayList<Item> list = display.getSelectedItems();
			if(list.size() > 0) {
				itemService.delete(cookies.getCookie("sid"), list, deleteCallback);
			}
		}
	};
	
	AsyncCallback<Void> deleteCallback = new AsyncCallback<Void>() {
		@Override
		public void onFailure(Throwable t) {
			handleThrowable(t);
		}

		@Override
		public void onSuccess(Void v) {
			// delete was a success, let's refresh the view
			refreshClickHandler.onClick(null);
		}
		
	};
	
	/**
	 * Treat all {@link Throwable} the same.  If it is an instance of
	 * {@link SessionTimedOutException} we navigate back to login page.  Otherwise
	 * we tell the display to show the error.
	 * 
	 * @param t
	 */
	private void handleThrowable(Throwable t) {
		try {
			throw t;
		} catch(SessionTimedOutException e) {
			// same as if user logged out
			logoutClickHandler.onClick(null);
		} catch (Throwable throwable) {
			display.setErrorMsg(throwable.getMessage());
		}
	}

}
