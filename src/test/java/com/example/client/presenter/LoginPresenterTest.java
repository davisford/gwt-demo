/**
 * 
 */
package com.example.client.presenter;

import static org.easymock.classextension.EasyMock.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.client.cookies.Cookies;
import com.example.client.event.EventBus;
import com.example.client.event.LoginEvent;
import com.example.client.mocks.MockHasClickHandlers;
import com.example.client.mocks.MockHasKeyDownHandlers;
import com.example.client.mocks.MockHasWidgets;
import com.example.client.model.User;
import com.example.client.service.UserServiceAsync;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Test class for {@link LoginPresenter}
 */
public class LoginPresenterTest {
	
	// dependencies class under test has
	private LoginPresenter.Display display;
	private EventBus eventBus;
	private Cookies cookies;
	private UserServiceAsync service;
	
	private MockHasWidgets container;
	private MockHasClickHandlers hasClickHandlers;
	private List<HasKeyDownHandlers> hasKeyDownHandlers;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		display = createMock(LoginPresenter.Display.class);
		eventBus = createMock(EventBus.class);
		cookies = createMock(Cookies.class);
		service = createMock(UserServiceAsync.class);
		
		container = new MockHasWidgets();
		hasClickHandlers = new MockHasClickHandlers();
		hasKeyDownHandlers = new ArrayList<HasKeyDownHandlers>();
		hasKeyDownHandlers.add(new MockHasKeyDownHandlers());
		
		// expectations through constructor/bind
		expect(display.loginButton()).andReturn(hasClickHandlers);
		expect(display.keyDownHandlers()).andReturn(hasKeyDownHandlers);
	}
	
	/**
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		assertEquals("expected click handler to be added ", 1, hasClickHandlers.addClickHandlerCnt);
		
		for(HasKeyDownHandlers h : hasKeyDownHandlers) {
			assertEquals("expected has key down handler to be added ", 1, ((MockHasKeyDownHandlers)h).addHandlerCount);
		}
	}

	private LoginPresenter getPresenter() {
		return new LoginPresenter(service, eventBus, cookies, display);
	}
	
	private void replayAll() {
		replay(service);
		replay(eventBus);
		replay(cookies);
		replay(display);
	}
	
	private void verifyAll() {
		verify(service);
		verify(eventBus);
		verify(cookies);
		verify(display);
	}
	
	/**
	 * Test method for {@link com.example.client.presenter.LoginPresenter#LoginPresenter(com.example.client.service.UserServiceAsync, com.google.gwt.event.shared.HandlerManager, com.example.client.presenter.LoginPresenter.Display)}.
	 */
	@Test
	public void testLoginPresenter() {
		replayAll();
		getPresenter();
		verifyAll();
	}

	/**
	 * Test method for {@link com.example.client.presenter.LoginPresenter#go(com.google.gwt.user.client.ui.HasWidgets)}.
	 */
	@Test
	public void testGo() {
		// we can't mock/stub Widget, so just return null
		expect(display.asWidget()).andReturn(null);
		replayAll();
		getPresenter().go(container);
		verifyAll();
		
		assertEquals("expected clear ", 1, container.clearCount);
		assertEquals("expected add ", 1, container.addCount);
	}
	
	/**
	 * Test method for {@link LoginPresenter#loginClickHandler}
	 */
	@Test
	public void testLoginClickHandlerUserInvalid() {
		display.setErrorMsg(null);
		expectLastCall().once();
		// user is invalid b/c no name/pass
		expect(display.getUser()).andReturn(new User());
		display.setErrorMsg("Please enter a username and password");
		expectLastCall().once();
		
		replayAll();
		getPresenter().loginClickHandler.onClick(null);
		verifyAll();
	}
	
	/**
	 * Test method for {@link LoginPresenter#loginClickHandler}
	 */
	@Test 
	public void testLoginClickHandlerUserValid() {
		User user = new User("name", "pass");
		display.setErrorMsg(null);
		expectLastCall().once();
		expect(display.getUser()).andReturn(user);
		service.login(eq(user), EasyMock.<AsyncCallback<String>>anyObject());
		expectLastCall().once();
		
		replayAll();
		getPresenter().loginClickHandler.onClick(null);
		verifyAll();
	}
	
	/**
	 * Test for {@link LoginPresenter#keyDownHandler}
	 */
	@Test
	public void testKeyDownHandlerUserInvalid() {
		display.setErrorMsg(null);
		expectLastCall().times(2);
		// user is invalid b/c no name/pass
		expect(display.getUser()).andReturn(new User());
		display.setErrorMsg("Please enter a username and password");
		expectLastCall().once();
		
		KeyDownEvent evt = createMock(KeyDownEvent.class);
		expect(evt.getNativeKeyCode()).andReturn(KeyCodes.KEY_ENTER);
		
		replay(evt);
		replayAll();
		getPresenter().keyDownHandler.onKeyDown(evt);
		verifyAll();
		verify(evt);
	}
	
	/**
	 * Test for {@link LoginPresenter#keyDownHandler}
	 */
	@Test
	public void testKeyDownHandlerUserValid() {
		display.setErrorMsg(null);
		expectLastCall().times(2);
		User user = new User("name", "pass");
		expect(display.getUser()).andReturn(user);
		expectLastCall().once();
		
		KeyDownEvent evt = createMock(KeyDownEvent.class);
		expect(evt.getNativeKeyCode()).andReturn(KeyCodes.KEY_ENTER);
		
		service.login(eq(user), EasyMock.<AsyncCallback<String>>anyObject());
		expectLastCall().once();
		
		replay(evt);
		replayAll();
		getPresenter().keyDownHandler.onKeyDown(evt);
		verifyAll();
		verify(evt);
	}
	
	/**
	 * Test for {@link LoginPresenter#callback}
	 */
	@Test
	public void testCallBackFailure() {
		Throwable t = new Exception("message");
		display.setErrorMsg("message"); 
		expectLastCall().once();
		
		replayAll();
		getPresenter().callback.onFailure(t);
		verifyAll();
	}
	
	/**
	 * Test for {@link LoginPresenter#callback}
	 */
	@Test
	public void testCallbackSuccess() {
		String sessionId = "123";
		String domain = null;
		
		Capture<Date> capturedDate = new Capture<Date>();
		cookies.setCookie(eq("sid"), eq(sessionId), capture(capturedDate), eq(domain), eq("/"), eq(false));
		expectLastCall().once();
		eventBus.fireEvent(EasyMock.<LoginEvent>anyObject());
		expectLastCall().once();
		
		replayAll();
		getPresenter().callback.onSuccess(sessionId);
		verifyAll();
	}

}
