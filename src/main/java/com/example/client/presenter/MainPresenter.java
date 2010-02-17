package com.example.client.presenter;

import java.util.ArrayList;

import com.example.client.cookies.Cookies;
import com.example.client.event.EventBus;
import com.example.client.event.ItemCreateEvent;
import com.example.client.event.ItemCreateEventHandler;
import com.example.client.event.ItemUpdateEvent;
import com.example.client.event.ItemUpdateEventHandler;
import com.example.client.event.LoginEvent;
import com.example.client.event.LoginEventHandler;
import com.example.client.event.SessionTimedOutEvent;
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
public class MainPresenter implements Presenter, SelectedItemListener {
	
	/**
	 * The interface {@link MainPresenter} uses to manipulate it's view class
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
	
	private ItemPresenter itemPresenter;
	
	/**
	 * Constructor
	 * <p>
	 * @param userService used to logout a user
	 * @param itemService used to do CRUD operations with server for {@link Item}
	 * @param eventBus application event bus
	 * @param cookies cookie management
	 * @param itemPresenter needed to show widget for create/edit of {@link Item}
	 * @param display our display class
	 */
	public MainPresenter(
			UserServiceAsync userService, 
			ItemServiceAsync itemService, 
			EventBus eventBus, 
			Cookies cookies, 
			ItemPresenter itemPresenter, 
			MainPresenter.Display display) {
		this.userService = userService;
		this.itemService = itemService;
		this.cookies = cookies;
		this.display = display;
		this.eventBus = eventBus;
		this.itemPresenter = itemPresenter;
		bind();
	}
	
	private void bind() {
		// let me know when login happens so i can update the welcome text and fetch the items
		eventBus.addHandler(LoginEvent.TYPE, loginHandler);
		// let me know when someone requests that a new item be created
		eventBus.addHandler(ItemCreateEvent.TYPE, createItemHandler);
		// let me know when someone requests that an item be updated
		eventBus.addHandler(ItemUpdateEvent.TYPE, updateItemHandler);
		// add handler when user clicks logout link
		display.logoutLink().addClickHandler(logoutClickHandler);
		// add handler when user clicks refresh button
		display.refresh().addClickHandler(refreshClickHandler);
		// add handler when user clicks delete button
		display.delete().addClickHandler(deleteClickHandler);
		// add handler when user clicks new button
		display.newItemClick().addClickHandler(newClickHandler);
		// add a callback on the view to let me know when the user selects a row
		display.setSelectedItemListener(this);
	}

	/* (non-Javadoc)
	 * @see com.example.client.presenter.Presenter#go(com.google.gwt.user.client.ui.HasWidgets)
	 */
	@Override
	public void go(HasWidgets container) {
		container.clear();
	    container.add(display.asWidget());
	}
	
	/**
	 * Handler that runs when a {@link LoginEvent} is fired
	 */
	LoginEventHandler loginHandler = new LoginEventHandler() {
		@Override
		public void onLogin(LoginEvent loginEvent) {
			display.setNameText(loginEvent.getUser().getUsername());
			
			// fetch the list of items
			itemService.findAll(cookies.getCookie("sid"), findCallback);
		}
	};
	
	/**
	 * Handler that runs when the user clicks the logout link
	 */
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
	
	/**
	 * Handler that runs when the user clicks the new button
	 */
	ClickHandler newClickHandler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent arg0) {
			itemPresenter.showView(new Item());
		}
	};
	
	/**
	 * Handler that runs when the user clicks the refresh button
	 */
	ClickHandler refreshClickHandler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent evt) {
			itemService.findAll(cookies.getCookie("sid"), findCallback);
		}
	};
	
	/**
	 * Callback for finding {@link Item}s from the server
	 */
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
	
	/**
	 * Handler that runs when the user clicks the delete button
	 */
	ClickHandler deleteClickHandler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent arg0) {
			final ArrayList<Item> list = display.getSelectedItems();
			if(list.size() > 0) {
				itemService.delete(cookies.getCookie("sid"), list, serviceCallback);
			}
		}
	};
	
	/**
	 * Generic callback that runs for most async service operations
	 */
	AsyncCallback<Void> serviceCallback = new AsyncCallback<Void>() {
		@Override
		public void onFailure(Throwable t) {
			handleThrowable(t);
		}

		@Override
		public void onSuccess(Void v) {
			// operation was a success, let's refresh the view
			refreshClickHandler.onClick(null);
		}
	};
	
	/**
	 * Handler that runs when {@link ItemCreateEvent} is fired
	 */
	ItemCreateEventHandler createItemHandler = new ItemCreateEventHandler() {
		@Override
		public void onCreate(ItemCreateEvent event) {
			itemService.create(cookies.getCookie("sid"), event.getItem(), serviceCallback);
		}
	};
	
	/**
	 * Handler that runs when {@link ItemUpdateEvent} is fired
	 */
	ItemUpdateEventHandler updateItemHandler = new ItemUpdateEventHandler() {
		@Override
		public void onUpdate(ItemUpdateEvent event) {
			itemService.update(cookies.getCookie("sid"), event.getItem(), serviceCallback);
		}
	};
	
	/**
	 * Treat all {@link java.lang.Throwable} the same.  If it is an instance of
	 * {@link SessionTimedOutException} we navigate back to login page.  Otherwise
	 * we tell the display to show the error.
	 * 
	 * @param t
	 */
	private void handleThrowable(Throwable t) {
		try {
			throw t;
		} catch(SessionTimedOutException e) {
			// this will do the appropriate logout on server if necessary
			logoutClickHandler.onClick(null);
			// this will navigate back to login screen
			eventBus.fireEvent(new SessionTimedOutEvent());
		} catch (Throwable throwable) {
			display.setErrorMsg(throwable.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.example.client.view.MainView.SelectedItemListener#onSelectedItem(com.example.client.model.Item)
	 */
	@Override
	public void onSelectedItem(Item item) {
		// the view is telling us that the user selected an item
		itemPresenter.showView(item);
	}
}
