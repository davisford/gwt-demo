/**
 * 
 */
package com.example.server.service;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.example.client.exception.ItemServiceException;
import com.example.client.exception.SessionTimedOutException;
import com.example.client.model.Item;
import com.example.client.service.ItemService;
import com.example.server.dao.ItemDao;

/**
 * Implementation of {@link ItemService}
 */
public class ItemServiceImpl extends AbstractService implements ItemService {
	
	private ItemDao itemDao;
	
	private static final Logger LOGGER = Logger.getLogger(ItemServiceImpl.class);

	/* (non-Javadoc)
	 * @see com.example.client.service.ItemService#create(java.lang.String, com.example.client.model.Item)
	 */
	@Override
	public void create(String sessionId, Item item)	throws SessionTimedOutException, ItemServiceException {
		try {
			itemDao.create(item);
		} catch(Exception ex) {
			LOGGER.error(ex);
			throw new ItemServiceException();
		}
	}

	/* (non-Javadoc)
	 * @see com.example.client.service.ItemService#delete(java.lang.String, com.example.client.model.Item)
	 */
	@Override
	public void delete(String sessionId, Item item)	throws SessionTimedOutException, ItemServiceException {
		try {
			itemDao.delete(item);
		} catch(Exception ex) {
			LOGGER.error(ex);
			throw new ItemServiceException();
		}
	}

	/* (non-Javadoc)
	 * @see com.example.client.service.ItemService#delete(java.lang.String, java.util.ArrayList)
	 */
	@Override
	public void delete(String sessionId, ArrayList<Item> items)	throws SessionTimedOutException, ItemServiceException {
		try {
			itemDao.delete(items);
		} catch(Exception ex) {
			LOGGER.error(ex);
			throw new ItemServiceException();
		}
	}

	/* (non-Javadoc)
	 * @see com.example.client.service.ItemService#findAll(java.lang.String)
	 */
	@Override
	public ArrayList<Item> findAll(String sessionId) throws SessionTimedOutException, ItemServiceException {
		try {
			return itemDao.findAll();
		} catch(Exception ex) {
			LOGGER.error(ex);
			throw new ItemServiceException();
		}
	}

	/* (non-Javadoc)
	 * @see com.example.client.service.ItemService#update(java.lang.String, com.example.client.model.Item)
	 */
	@Override
	public void update(String sessionId, Item item)	throws SessionTimedOutException, ItemServiceException {
		try {
			itemDao.update(item);
		} catch(Exception ex) {
			LOGGER.error(ex);
			throw new ItemServiceException();
		}
	}
	
	/**
	 * Injected via spring
	 * @param dao
	 */
	public void setItemDao(ItemDao dao) {
		this.itemDao = dao;
	}

}
