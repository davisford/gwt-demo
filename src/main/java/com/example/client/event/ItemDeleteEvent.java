package com.example.client.event;

import java.util.ArrayList;

import com.example.client.model.Item;
import com.google.gwt.event.shared.GwtEvent;

public class ItemDeleteEvent extends GwtEvent<ItemDeleteEventHandler> {
	
	public static Type<ItemDeleteEventHandler> TYPE = new Type<ItemDeleteEventHandler>();
	
	private ArrayList<Item> items = new ArrayList<Item>();
	
	public ItemDeleteEvent(ArrayList<Item> listToDelete) {
		items.addAll(items);
	}
	
	public ArrayList<Item> getItemsToDelete() {
		return items;
	}

	@Override
	protected void dispatch(ItemDeleteEventHandler handler) {
		handler.onDelete(this);
	}

	@Override
	public Type<ItemDeleteEventHandler> getAssociatedType() {
		return TYPE;
	}

}
