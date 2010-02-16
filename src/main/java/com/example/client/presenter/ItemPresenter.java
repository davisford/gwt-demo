package com.example.client.presenter;

import com.example.client.event.EventBus;
import com.example.client.event.ItemCreateEvent;
import com.example.client.event.ItemUpdateEvent;
import com.example.client.model.Item;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasWidgets;

/**
 * Presenter class for Item View
 */
public class ItemPresenter implements Presenter {
	
	/**
	 * The interface {@link ItemPresenter} uses to manipulate it's view class
	 */
	public interface Display extends WidgetDisplay {
		
		/**
		 * Get the item to be saved
		 * @return
		 */
		Item getItem();
		
		/**
		 * Attach a handler to the save widget
		 * @return
		 */
		HasClickHandlers save();
		
		/**
		 * Attach a handler to the cancel widget
		 * @return
		 */
		HasClickHandlers cancel();
		
		/**
		 * Tell the display to remove the popup
		 */
		void removePopUp();
		
		/**
		 * Tell the display to show the popup
		 */
		void showPopUp(Item item);

	}
	
	private Display display;
	private EventBus eventBus;
	
	/**
	 * Constructor
	 * <p>
	 * @param eventBus
	 * @param display
	 */
	public ItemPresenter(EventBus eventBus, ItemPresenter.Display display) {
		this.eventBus = eventBus;
		this.display = display;
		display.save().addClickHandler(saveHandler);
		display.cancel().addClickHandler(cancelHandler);
	}
	
	/**
	 * Allows other presenters to instruct this instance to show a view for a edit/create and {@link Item}
	 * @param item the item to be created or edited
	 */
	public void showView(Item item) {
		display.showPopUp(item);
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
	 * The handler that runs when the save button is clicked
	 */
	ClickHandler saveHandler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent evt) {
			Item item = display.getItem();
			if(item.getId() > 0) {
				eventBus.fireEvent(new ItemUpdateEvent(item));
			} else {
				eventBus.fireEvent(new ItemCreateEvent(item));
			}
			display.removePopUp();
		}
	};
	
	/**
	 * The handler that runs when the cancel button is clicked
	 */
	ClickHandler cancelHandler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent arg0) {
			display.removePopUp();
		}
	};

}
