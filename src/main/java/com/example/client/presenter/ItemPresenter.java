/**
 * 
 */
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
 * @author davisford
 *
 */
public class ItemPresenter implements Presenter {
	
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
	
	public ItemPresenter(EventBus eventBus, ItemPresenter.Display display) {
		this.eventBus = eventBus;
		this.display = display;
		display.save().addClickHandler(saveHandler);
		display.cancel().addClickHandler(cancelHandler);
	}
	
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
	
	ClickHandler cancelHandler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent arg0) {
			display.removePopUp();
		}
		
	};

}
