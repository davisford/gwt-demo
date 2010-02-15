package com.example.client.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link Item}
 */
public class ItemTest {
	
	private Item item;
	
	private static final String NAME = "name";
	private static final String DESC = "description";
	private static final Date DATE = new Date();
	
	@Before
	public void setUp() {
		item = new Item();
	}

	@Test
	public void testHashCode() {
		BeanTestCase.assertMeetsHashCodeContract(Item.class);
	}

	@Test
	public void testItem() {
		// not interesting
	}

	@Test
	public void testItemStringStringDate() {
		item = new Item(NAME, DESC, DATE);
		assertEquals(NAME, item.getName());
		assertEquals(DESC, item.getDescription());
		assertEquals(DATE, item.getDate());
	}

	@Test
	public void testGetId() {
		assertEquals(0, item.getId());
	}

	@Test
	public void testSetId() {
		item.setId(1L);
		assertEquals(1L, item.getId());
	}

	@Test
	public void testGetName() {
		assertNull(item.getName());
	}

	@Test
	public void testSetName() {
		item.setName(NAME);
		assertEquals(NAME, item.getName());
	}

	@Test
	public void testGetDescription() {
		assertNull(item.getDescription());
	}

	@Test
	public void testSetDescription() {
		item.setDescription(DESC);
		assertEquals(DESC, item.getDescription());
	}

	@Test
	public void testGetDate() {
		assertNotNull("Date should never be null", item.getDate());
	}

	@Test
	public void testSetDate() {
		item.setDate(DATE);
		assertEquals(DATE, item.getDate());
	}

	@Test
	public void testEqualsObject() {
		BeanTestCase.assertMeetsEqualsContract(Item.class);
	}

}
