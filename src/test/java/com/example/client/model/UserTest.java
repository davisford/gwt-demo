package com.example.client.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class UserTest {
	
	private User user;
	private static final String NAME = "name";
	private static final String PASS = "pass";
	
	@Before
	public void setUp() {
		user = new User();
	}

	@Test
	public void testHashCode() {
		BeanTestCase.assertMeetsHashCodeContract(User.class);
	}

	@Test
	public void testUser() {
		// not interesting
	}

	@Test
	public void testUserStringString() {
		user = new User(NAME, PASS);
		assertEquals(NAME, user.getUsername());
		assertEquals(PASS, user.getPassword());
	}

	@Test
	public void testIsValid() {
		assertFalse(user.isValid());
		user.setUsername(NAME);
		assertFalse(user.isValid());
		user.setUsername(null);
		user.setPassword(PASS);
		assertFalse(user.isValid());
		user.setUsername(NAME);
		user.setPassword(PASS);
		assertTrue(user.isValid());
	}

	@Test
	public void testGetUsername() {
		assertNull(user.getUsername());
	}

	@Test
	public void testSetUsername() {
		user.setUsername(NAME);
		assertEquals(NAME, user.getUsername());
	}

	@Test
	public void testGetPassword() {
		assertNull(user.getPassword());
	}

	@Test
	public void testSetPassword() {
		user.setPassword(PASS);
		assertEquals(PASS, user.getPassword());
	}

	@Test
	public void testEqualsObject() {
		BeanTestCase.assertMeetsEqualsContract(User.class);
	}

}
