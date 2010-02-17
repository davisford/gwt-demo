/**
 * 
 */
package com.example.server.service;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.client.model.User;

/**
 * Test class for {@link AbstractServiceTest}
 */
public class AbstractServiceTest {
	
	// class under test
	private Service service;
	
	private HttpSession httpSession;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		service = new Service();
		httpSession = createMock(HttpSession.class);
		Service.sessionMap.clear();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.example.server.service.AbstractService#setHttpSession(javax.servlet.http.HttpSession)}.
	 */
	@Test
	public void testSetHttpSession() {
		service.setHttpSession(httpSession);
		assertEquals(httpSession, service.httpSession);
	}

	/**
	 * Test method for {@link com.example.server.service.AbstractService#isSessionValid(java.lang.String)}.
	 */
	@Test
	public void testIsSessionValidSessionIdsDontMatch() {
		service.setHttpSession(httpSession);
		
		// this means it was some XSRF trickery
		expect(httpSession.getId()).andReturn("234");
		Service.sessionMap.put("123", new User());
		replay(httpSession);
		assertFalse(service.isSessionValid("123"));
		verify(httpSession);
	}
	
	@Test
	public void testIsSessionValidSessioMapDoesNotHaveSession() {
		service.setHttpSession(httpSession);
		
		// this means the server expired the session
		assertFalse(service.isSessionValid("123"));
	}
	
	@Test
	public void testIsSessionValid() {
		service.setHttpSession(httpSession);
		
		// this is the happy path
		expect(httpSession.getId()).andReturn("123");
		Service.sessionMap.put("123", new User());
		replay(httpSession);
		assertTrue(service.isSessionValid("123"));
		verify(httpSession);
	}
	
	private class Service extends AbstractService {
	}

}
