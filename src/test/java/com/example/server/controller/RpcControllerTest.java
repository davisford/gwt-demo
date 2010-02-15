/**
 * 
 */
package com.example.server.controller;

import static org.easymock.classextension.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.example.client.exception.SessionTimedOutException;
import com.example.server.service.AbstractService;
import com.google.gwt.user.client.rpc.RemoteService;


/**
 * Test class for {@link RpcController}.  We don't cover all
 * methods, b/c we can't mock out GWT/RPC static usage.  The important
 * method to test is the security check, which is covered here.
 */
public class RpcControllerTest {

	// class under test
	private RpcController controller;
	private MockService baseService;
	
	@Before
	public void setUp() {
		baseService = createMock(MockService.class);
		controller = new RpcController();
		controller.setService(baseService);
	}
	
	@Test(expected = SessionTimedOutException.class)
	public void testValidateSessionNull() throws SessionTimedOutException {
		controller.validateSession(null);
	}
	
	@Test(expected = SessionTimedOutException.class)
	public void testValidateSessionEmptyParams() throws SessionTimedOutException {
		controller.validateSession(new Object[0]);
	}
	
	@Test(expected = SessionTimedOutException.class)
	public void testValidateSessionFirstParamIsNotString() throws SessionTimedOutException {
		controller.validateSession(new Object[]{new Object()});
	}
	
	@Test
	public void testValidateSessionTimedOut(){
		expect(baseService.isSessionValid("123")).andReturn(false);
		replay(baseService);
		try {
			controller.validateSession(new Object[]{"123"});
			fail("expected exception");
		} catch(SessionTimedOutException e) {
			verify(baseService);
		}
	}
	
	@Test
	public void testValidateSessionNotTimedOut() throws SessionTimedOutException {
		expect(baseService.isSessionValid("123")).andReturn(true);
		replay(baseService);
		controller.validateSession(new Object[] {"123"});
		verify(baseService);
	}
	
	private class MockService extends AbstractService implements RemoteService {
		
	}
	
}
