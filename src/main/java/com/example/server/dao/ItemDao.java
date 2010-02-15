package com.example.server.dao;

import java.util.ArrayList;

import com.example.client.model.Item;

public interface ItemDao {
	
	ArrayList<Item> findAll();
	
	/**
	 * Create a new {@link Item} and persist it
	 * @param item
	 */
	void create(Item item);
	
	/**
	 * Update an existing {@link Item}
	 * @param item
	 */
	void update(Item item);

	
	/**
	 * Delete a list of {@link Item}s
	 * @param items
	 */
	void delete(ArrayList<Item> items);

}
