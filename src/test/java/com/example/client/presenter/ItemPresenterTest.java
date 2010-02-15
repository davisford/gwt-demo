/**
 * 
 */
package com.example.client.presenter;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import org.easymock.Capture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.client.event.EventBus;
import com.example.client.event.ItemCreateEvent;
import com.example.client.event.ItemUpdateEvent;
import com.example.client.mocks.MockHasClickHandlers;
import com.example.client.mocks.MockHasWidgets;
import com.example.client.model.Item;

/**
 * Test class for {@link ItemPresenter}
 */
public class ItemPresenterTest {

	private ItemPresenter.Display display;
	private EventBus eventBus;
	
	private MockHasClickHandlers saveClick;
	private MockHasClickHandlers cancelClick;
	private MockHasWidgets container;
	
	@Before
	public void setUp() throws Exception {
		display = createMock(ItemPresenter.Display.class);
		eventBus = createMock(EventBus.class);
		
		saveClick = new MockHasClickHandlers();
		cancelClick = new MockHasClickHandlers();
		
		container = new MockHasWidgets();
		
		// expectations through constructor/bind
		expect(display.save()).andReturn(saveClick);
		expect(display.cancel()).andReturn(cancelClick);
	}
	
	@After
	public void tearDown() throws Exception {
		assertEquals("expected save click handler to be added ", 1, saveClick.addClickHandlerCnt);
		assertEquals("expected cancel click handler to be added ", 1, cancelClick.addClickHandlerCnt);
	}
	
	private ItemPresenter getPresenter() {
		return new ItemPresenter(eventBus, display);
	}
	
	private void replayAll() {
		replay(display);
		replay(eventBus);
	}
	
	private void verifyAll() {
		verify(display);
		verify(eventBus);
	}
	
	/**
	 * Test method for {@link com.example.client.presenter.ItemPresenter#ItemPresenter(com.example.client.event.EventBus, com.example.client.presenter.ItemPresenter.Display)}.
	 */
	@Test
	public void testItemPresenter() {
		replayAll();
		getPresenter();
		verifyAll();
	}

	/**
	 * Test method for {@link com.example.client.presenter.ItemPresenter#showView(com.example.client.model.Item)}.
	 */
	@Test
	public void testShowView() {
		Item item = new Item();
		display.showPopUp(item);
		expectLastCall().once();
		
		replayAll();
		getPresenter().showView(item);
		verifyAll();
	}

	/**
	 * Test method for {@link com.example.client.presenter.ItemPresenter#go(com.google.gwt.user.client.ui.HasWidgets)}.
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
	
	@Test
	public void testSaveHandlerCreate() {
		Item item = new Item();
		expect(display.getItem()).andReturn(item);
		
		Capture<ItemCreateEvent> capturedEvent = new Capture<ItemCreateEvent>();
		eventBus.fireEvent(capture(capturedEvent));
		expectLastCall().once();
		display.removePopUp();
		expectLastCall().once();
		
		replayAll();
		getPresenter().saveHandler.onClick(null);
		verifyAll();
		
		assertEquals(item, capturedEvent.getValue().getItem());
	}
	
	@Test
	public void testSaveHandlerUpdate() {
		Item item = new Item();
		item.setId(1L);
		expect(display.getItem()).andReturn(item);
		
		Capture<ItemUpdateEvent> capturedEvent = new Capture<ItemUpdateEvent>();
		eventBus.fireEvent(capture(capturedEvent));
		expectLastCall().once();
		display.removePopUp();
		expectLastCall().once();
		
		replayAll();
		getPresenter().saveHandler.onClick(null);
		verifyAll();
		
		assertEquals(item, capturedEvent.getValue().getItem());
	}
	
	@Test
	public void testClickHandler() {
		display.removePopUp();
		expectLastCall().once();
		
		replayAll();
		getPresenter().cancelHandler.onClick(null);
		verifyAll();
	}

}
