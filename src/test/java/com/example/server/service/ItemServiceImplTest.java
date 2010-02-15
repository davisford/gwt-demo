/**
 * 
 */
package com.example.server.service;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

import com.example.client.exception.ItemServiceException;
import com.example.client.exception.SessionTimedOutException;
import com.example.client.model.Item;
import com.example.server.dao.ItemDao;

/**
 * Test class for {@link ItemServiceImpl}
 */
public class ItemServiceImplTest {
	
	// class under test
	private ItemServiceImpl service;
	
	private ItemDao dao;
	private HttpSession httpSession;
	
	private Item item;
	
	@Before
	public void setUp() {
		dao = createMock(ItemDao.class);
		httpSession = createMock(HttpSession.class);
		
		item = new Item();
		
		service = new ItemServiceImpl();
		service.setItemDao(dao);
		service.setHttpSession(httpSession);
	}
	
	public void replayAll() {
		replay(dao);
		replay(httpSession);
	}
	
	public void verifyAll() {
		verify(dao);
		verify(httpSession);
	}

	/**
	 * Test method for {@link com.example.server.service.ItemServiceImpl#create(java.lang.String, com.example.client.model.Item)}.
	 * @throws ItemServiceException 
	 * @throws SessionTimedOutException 
	 */
	@Test
	public void testCreate() throws SessionTimedOutException, ItemServiceException {
		dao.create(item);
		expectLastCall().once();
		
		replayAll();
		service.create(null, item);
		verifyAll();
	}
	
	@Test(expected = ItemServiceException.class)
	public void testCreateWithException() throws SessionTimedOutException, ItemServiceException {
		dao.create(item);
		expectLastCall().andThrow(new RuntimeException("EasyMock threw this"));
		
		replayAll();
		service.create(null, item);
		verifyAll();
	}

	/**
	 * Test method for {@link com.example.server.service.ItemServiceImpl#delete(java.lang.String, java.util.ArrayList)}.
	 * @throws ItemServiceException 
	 * @throws SessionTimedOutException 
	 */
	@Test
	public void testDeleteStringArrayListOfItem() throws SessionTimedOutException, ItemServiceException {
		ArrayList<Item> list = new ArrayList<Item>();
		list.add(item);
		
		dao.delete(list);
		expectLastCall().once();
		
		replayAll();
		service.delete(null, list);
		verifyAll();
	}
	
	@Test(expected = ItemServiceException.class)
	public void testDeleteStringArrayListOfItemWithException() throws SessionTimedOutException, ItemServiceException {
		dao.delete(null);
		expectLastCall().andThrow(new RuntimeException("EasyMock threw this"));
		
		replayAll();
		service.delete(null, null);
		verifyAll();
	}

	/**
	 * Test method for {@link com.example.server.service.ItemServiceImpl#findAll(java.lang.String)}.
	 * @throws ItemServiceException 
	 * @throws SessionTimedOutException 
	 */
	@Test
	public void testFindAll() throws SessionTimedOutException, ItemServiceException {
		ArrayList<Item> list = new ArrayList<Item>();
		list.add(item);
		expect(dao.findAll()).andReturn(list);
		
		replayAll();
		ArrayList<Item> actual = service.findAll(null);
		verifyAll();
	
		assertEquals(list, actual);
	}
	
	@Test(expected = ItemServiceException.class)
	public void testFindAllWithException() throws SessionTimedOutException, ItemServiceException {
		expect(dao.findAll()).andThrow(new RuntimeException("EasyMock threw this"));
		
		replayAll();
		service.findAll(null);
		verifyAll();
	}

	/**
	 * Test method for {@link com.example.server.service.ItemServiceImpl#update(java.lang.String, com.example.client.model.Item)}.
	 * @throws ItemServiceException 
	 * @throws SessionTimedOutException 
	 */
	@Test
	public void testUpdate() throws SessionTimedOutException, ItemServiceException {
		dao.update(item);
		expectLastCall().once();
		
		replayAll();
		service.update(null, item);
		verifyAll();
	}
	
	@Test(expected = ItemServiceException.class)
	public void testUpdateWithException() throws SessionTimedOutException, ItemServiceException {
		dao.update(item);
		expectLastCall().andThrow(new RuntimeException("EasyMock threw this"));
		
		replayAll();
		service.update(null, item);
		verifyAll();
	}

}
