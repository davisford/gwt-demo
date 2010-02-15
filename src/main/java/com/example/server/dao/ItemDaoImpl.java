/**
 * 
 */
package com.example.server.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.example.client.model.Item;

/**
 * Dumb implementation of {@link ItemDao} that does not persist anything.
 */
public class ItemDaoImpl implements ItemDao {
	
	private Map<Long, Item> map = new ConcurrentHashMap<Long, Item>();
	
	private AtomicLong sequence = new AtomicLong();
	
	public ItemDaoImpl() {
		// create a bunch of mock items
		createItems();
	}

	/* (non-Javadoc)
	 * @see com.example.server.dao.ItemDao#create(com.example.client.model.Item)
	 */
	@Override
	public void create(Item item) {
		if(item == null) { return; }
		
		item.setId(sequence.incrementAndGet());
		map.put(item.getId(), item);
	}

	/* (non-Javadoc)
	 * @see com.example.server.dao.ItemDao#delete(java.util.ArrayList)
	 */
	@Override
	public void delete(ArrayList<Item> items) {
		if(items == null || items.size() == 0) { return; }
		
		for(Item i : items) {
			map.remove(i.getId());
		}
	}

	/* (non-Javadoc)
	 * @see com.example.server.dao.ItemDao#findAll()
	 */
	@Override
	public ArrayList<Item> findAll() {
		final ArrayList<Item> list = new ArrayList<Item>(map.size());
		list.addAll(map.values());
		return list;
	}

	/* (non-Javadoc)
	 * @see com.example.server.dao.ItemDao#update(com.example.client.model.Item)
	 */
	@Override
	public void update(Item item) {
		if(item == null || !(map.containsKey(item.getId()))) { return; }
		map.put(item.getId(), item);
	}
	
	/**
	 * Create a bunch of mock item data
	 */
	private void createItems() {
		Random r = new Random();
		r.setSeed(System.currentTimeMillis() + System.nanoTime());
		
		create(new Item("Item 1", "Description of Item 1", randomDate()));
		create(new Item("Item 2", "Description of Item 2", randomDate()));
		create(new Item("Foo", "Foo's description", randomDate()));
		create(new Item("Bar", "Bar's description", randomDate()));
		create(new Item("Baz", "Baz's description", randomDate()));
		create(new Item("Widget", "Widget's description", randomDate()));
		create(new Item("FooBar", "Description of FooBar ", randomDate()));
		create(new Item("BarFoo", "Bar Foo", randomDate()));
		create(new Item("FooBaz", "Foo Baz", randomDate()));
	}
	
	final Random rand = new Random(System.currentTimeMillis());
	private Date randomDate() {
		final long l = Math.abs((long) rand.nextInt()) * 1000;
		return new Date(l);
	}

}
