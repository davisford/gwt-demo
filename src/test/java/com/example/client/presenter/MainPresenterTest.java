/**
 * 
 */
package com.example.client.presenter;

import static org.easymock.classextension.EasyMock.*;
import static org.junit.Assert.fail;

import org.easymock.Capture;
import org.easymock.classextension.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.example.client.cookies.Cookies;
import com.example.client.event.EventBus;
import com.example.client.event.ItemCreateEvent;
import com.example.client.event.ItemCreateEventHandler;
import com.example.client.event.LoginEvent;
import com.example.client.event.LoginEventHandler;
import com.example.client.mocks.MockHasClickHandlers;
import com.example.client.mocks.MockHasWidgets;
import com.example.client.service.ItemServiceAsync;
import com.example.client.service.UserServiceAsync;
import com.example.client.view.MainView.SelectedItemListener;

/**
 * Test class for {@link MainPresenterTest} 
 * 
 * TODO: finish
 */
public class MainPresenterTest {
	
	private MainPresenter.Display display;
	private EventBus eventBus;
	private Cookies cookies;
	private UserServiceAsync userService;
	private ItemServiceAsync itemService;
	private ItemPresenter itemPresenter;
	
	private MockHasWidgets container;
	private MockHasClickHandlers logoutClickHandlers;
	private MockHasClickHandlers refreshClickHandlers;
	private MockHasClickHandlers deleteClickHandlers;
	private MockHasClickHandlers newClickHandlers;
	
	@Before
	public void setUp() throws Exception {
		display = createMock(MainPresenter.Display.class);
		eventBus = createMock(EventBus.class);
		cookies = createMock(Cookies.class);
		userService = createMock(UserServiceAsync.class);
		itemService = createMock(ItemServiceAsync.class);
		itemPresenter = createMock(ItemPresenter.class);
		
		container = new MockHasWidgets();
		logoutClickHandlers = new MockHasClickHandlers();
		refreshClickHandlers = new MockHasClickHandlers();
		deleteClickHandlers = new MockHasClickHandlers();
		newClickHandlers = new MockHasClickHandlers();
		
		expect(display.logoutLink()).andReturn(logoutClickHandlers);
		expect(display.refresh()).andReturn(refreshClickHandlers);
		expect(display.delete()).andReturn(deleteClickHandlers);
		expect(display.newItemClick()).andReturn(newClickHandlers);
		display.setSelectedItemListener(EasyMock.<SelectedItemListener>anyObject());
		expectLastCall().once();

		//expect(eventBus.addHandler(eq(LoginEvent.TYPE), EasyMock.<LoginEventHandler>anyObject()));
		//expect(eventBus.addHandler(eq(ItemCreateEvent.TYPE), EasyMock.<ItemCreateEventHandler>anyObject()));
		//expect(eventBus.addHandler(eq(ItemUpdateEvent.TYPE), EasyMock.<ItemUpdateEventHandler>anyObject()));
	}
	
	private MainPresenter getPresenter() {
		return new MainPresenter(userService, itemService, eventBus, cookies, itemPresenter, display);
	}
	
	private void replayAll() {
		replay(display);
		replay(eventBus);
		replay(cookies);
		replay(userService);
		replay(itemService);
		replay(itemPresenter);
	}
	
	private void verifyAll() {
		verify(display);
		verify(eventBus);
		verify(cookies);
		verify(userService);
		verify(itemService);
		verify(itemPresenter);
	}

	/**
	 * Test method for {@link com.example.client.presenter.MainPresenter#MainPresenter(com.example.client.service.UserServiceAsync, com.example.client.service.ItemServiceAsync, com.example.client.event.EventBus, com.example.client.cookies.Cookies, com.example.client.presenter.ItemPresenter, com.example.client.presenter.MainPresenter.Display)}.
	 */
	@Test
	public void testMainPresenter() {
		replayAll();
		getPresenter();
		verifyAll();
	}

	/**
	 * Test method for {@link com.example.client.presenter.MainPresenter#go(com.google.gwt.user.client.ui.HasWidgets)}.
	 */
	@Test
	public void testGo() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.example.client.presenter.MainPresenter#onSelectedItem(com.example.client.model.Item)}.
	 */
	@Test
	public void testOnSelectedItem() {
		fail("Not yet implemented");
	}

}
