/**
 * 
 */
package com.example.server.service;

import static org.easymock.classextension.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;

import org.junit.Before;
import org.junit.Test;

import com.example.client.exception.LoginFailureException;
import com.example.client.model.User;
import com.example.client.service.UserService;
import com.example.server.dao.UserDao;
import com.example.server.security.BCrypt;

/**
 * Test class for {@link UserService}
 */
public class UserServiceImplTest {
	
	private UserServiceImpl service;
	
	private UserDao dao;
	private HttpSession httpSession;
	
	private User user;
	
	@Before 
	public void setUp() {
		dao = createMock(UserDao.class);
		httpSession = createMock(HttpSession.class);
		
		service = new UserServiceImpl();
		
		service.setUserDao(dao);
		
		user = new User("davis", BCrypt.hashpw("davis", BCrypt.gensalt()));
	
		httpSession.setAttribute("SessionBindingListener", service);
		expectLastCall().once();
	}
	
	private void replayAll() {
		replay(dao);
		replay(httpSession);
		service.setHttpSession(httpSession);
	}
	
	private void verifyAll() {
		verify(dao);
		verify(httpSession);
	}

	/**
	 * Test method for {@link com.example.server.service.UserServiceImpl#setHttpSession(javax.servlet.http.HttpSession)}.
	 */
	@Test
	public void testSetHttpSession() {
		replayAll();
		assertEquals(service.httpSession, httpSession);
		verifyAll();
	}

	/**
	 * Test method for {@link com.example.server.service.UserServiceImpl#login(com.example.client.model.User)}.
	 * @throws LoginFailureException 
	 */
	@Test(expected = LoginFailureException.class)
	public void testLoginNullUser() throws LoginFailureException {
		service.login(null);
	}
	
	@Test(expected = LoginFailureException.class)
	public void testLoginInvalidUser() throws LoginFailureException {
		service.login(new User());
	}
	
	@Test
	public void testLoginUserNotFound() {
		expect(dao.findByUserName("davis")).andReturn(null);
		
		replayAll();
		try {
			service.login(user);
			fail("expected exception");
		} catch (LoginFailureException e) {
			verifyAll();
		}
		
	}
	
	@Test 
	public void testLoginPasswordIsWrong() {
		User foundUser = new User("davis", "wrong-password");
		expect(dao.findByUserName("davis")).andReturn(foundUser);
		
		replayAll();
		try {
			service.login(user);
			fail("expected exception");
		} catch(LoginFailureException e) {
			verifyAll();
		}
	}
	
	@Test
	public void testLoginSuccess() throws LoginFailureException {
		expect(dao.findByUserName("davis")).andReturn(user);
		expect(httpSession.getId()).andReturn("123");
		
		replayAll();
		String id = service.login(new User("davis", "davis"));
		verifyAll();
		
		assertEquals(id, "123");
		User actual = service.isLoggedIn("123");
		assertNull("Expect service to null out password ", actual.getPassword());
		assertEquals("davis", actual.getUsername());
	}
	
	@Test
	public void testLoginThrowsException()  {
		expect(dao.findByUserName("davis")).andThrow(new RuntimeException("EasyMock threw this"));
		
		replayAll();
		try {
			service.login(new User("davis", "davis"));
			fail("expected exception here");
		} catch(LoginFailureException e) {
			verifyAll();
		}	
	}

	/**
	 * Test method for {@link com.example.server.service.UserServiceImpl#logout(java.lang.String)}.
	 */
	@Test
	public void testLogout() {
		expect(httpSession.getId()).andReturn("123");
		httpSession.invalidate();
		expectLastCall().once();
		
		replayAll();
		service.logout("123");
		verifyAll();
		
		try {
			service.isLoggedIn("123");
			fail("expected exception");
		} catch(LoginFailureException e) {
			// expected
		}
	}

	/**
	 * Test method for {@link com.example.server.service.UserServiceImpl#isLoggedIn(java.lang.String)}.
	 * @throws LoginFailureException 
	 */
	@Test(expected = LoginFailureException.class)
	public void testIsLoggedIn() throws LoginFailureException {
		service.isLoggedIn("123");
	}

	/**
	 * Test method for {@link com.example.server.service.UserServiceImpl#valueBound(javax.servlet.http.HttpSessionBindingEvent)}.
	 */
	@Test
	public void testValueBound() {
		// not interesting
	}

	/**
	 * Test method for {@link com.example.server.service.UserServiceImpl#valueUnbound(javax.servlet.http.HttpSessionBindingEvent)}.
	 * @throws LoginFailureException 
	 */
	@Test
	public void testValueUnbound() throws LoginFailureException {
		expect(dao.findByUserName("davis")).andReturn(user);
		expect(httpSession.getId()).andReturn("123");
		
		replayAll();
		String id = service.login(new User("davis", "davis"));
		verifyAll();
		
		assertEquals(id, "123");
		User actual = service.isLoggedIn("123");
		assertNull("Expect service to null out password ", actual.getPassword());
		assertEquals("davis", actual.getUsername());
		
		HttpSessionBindingEvent event = createMock(HttpSessionBindingEvent.class);
		HttpSession mockSession = createMock(HttpSession.class);
		expect(event.getSession()).andReturn(mockSession);
		expect(mockSession.getId()).andReturn("123");
		
		replay(event);
		replay(mockSession);
		service.valueUnbound(event);
		
		try {
			service.isLoggedIn("123");
			fail("expected exception");
		} catch(LoginFailureException e) {
			verify(event);
			verify(mockSession);
		}
	}

}
