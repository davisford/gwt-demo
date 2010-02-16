package com.example.client.service;

import java.util.ArrayList;

import com.example.client.exception.ItemServiceException;
import com.example.client.exception.SessionTimedOutException;
import com.example.client.model.Item;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Service for doing CRUD operations on {@link Item}
 */
@RemoteServiceRelativePath("item.rpc")
public interface ItemService extends RemoteService {

	/**
	 * Finds all {@link Item}s 
	 * 
	 * @param sessionId
	 * @return
	 */
	ArrayList<Item> findAll(String sessionId) throws SessionTimedOutException, ItemServiceException;
	
	/**
	 * Create a new {@link Item} and persist it
	 * 
	 * @param sessionId
	 * @param item
	 */
	void create(String sessionId, Item item) throws SessionTimedOutException, ItemServiceException;
	
	/**
	 * Update an existing {@link Item}
	 * 
	 * @param sessionId
	 * @param item
	 */
	void update(String sessionId, Item item) throws SessionTimedOutException, ItemServiceException;
	
	/**
	 * Delete a list of {@link Item}s
	 * 
	 * @param sessionId
	 * @param items
	 */
	void delete(String sessionId, ArrayList<Item> items) throws SessionTimedOutException, ItemServiceException;
}
