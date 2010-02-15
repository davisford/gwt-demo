package com.example.client.service;

import java.util.ArrayList;

import com.example.client.model.Item;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Asynchronous version of {@link ItemService}
 */
public interface ItemServiceAsync {

	/**
	 * @see {@link ItemService#create(String, Item)}
	 */
	void create(String sessionId, Item item, AsyncCallback<Void> callback);

	/**
	 * @see {@link ItemService#delete(String, Item)}
	 */
	void delete(String sessionId, Item item, AsyncCallback<Void> callback);

	/**
	 * @see {@link ItemService#delete(String, ArrayList)}
	 */
	void delete(String sessionId, ArrayList<Item> items, AsyncCallback<Void> callback);

	/**
	 * @see {@link ItemService#findAll(String)}
	 */
	void findAll(String sessionId, AsyncCallback<ArrayList<Item>> callback);

	/**
	 * @see {@link ItemService#update(String, Item)}
	 */
	void update(String sessionId, Item item, AsyncCallback<Void> callback);

}
