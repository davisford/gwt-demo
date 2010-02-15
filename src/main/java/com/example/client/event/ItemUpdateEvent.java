package com.example.client.event;

import com.example.client.model.Item;
import com.google.gwt.event.shared.GwtEvent;

public class ItemUpdateEvent extends GwtEvent<ItemUpdateEventHandler> {
	
	public static Type<ItemUpdateEventHandler> TYPE = new Type<ItemUpdateEventHandler>();
	
	private Item item;
	
	public ItemUpdateEvent(Item item) {
		this.item = item;
	}
	
	public Item getItem() {
		return item;
	}

	@Override
	protected void dispatch(ItemUpdateEventHandler handler) {
		handler.onUpdate(this);
	}

	@Override
	public Type<ItemUpdateEventHandler> getAssociatedType() {
		return TYPE;
	}

}
