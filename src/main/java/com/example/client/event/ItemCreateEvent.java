package com.example.client.event;

import com.example.client.model.Item;
import com.google.gwt.event.shared.GwtEvent;

public class ItemCreateEvent extends GwtEvent<ItemCreateEventHandler> {
	
	public static Type<ItemCreateEventHandler> TYPE = new Type<ItemCreateEventHandler>();
	
	private Item item;
	
	public ItemCreateEvent(Item item) {
		this.item = item;
	}
	
	public Item getItem() {
		return item;
	}

	@Override
	protected void dispatch(ItemCreateEventHandler handler) {
		handler.onCreate(this);
	}

	@Override
	public Type<ItemCreateEventHandler> getAssociatedType() {
		return TYPE;
	}

}
