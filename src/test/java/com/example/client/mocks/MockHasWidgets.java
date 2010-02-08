/**
 * 
 */
package com.example.client.mocks;

import java.util.Iterator;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * Mock implementation of {@link HasWidgets}
 */
public class MockHasWidgets implements HasWidgets {
	
	public int addCount;
	public int clearCount;
	public int removeCount;

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#add(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public void add(Widget arg0) {
		addCount++;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#clear()
	 */
	@Override
	public void clear() {
		clearCount++;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#iterator()
	 */
	@Override
	public Iterator<Widget> iterator() {
		throw new UnsupportedOperationException("not implemented");
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#remove(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean remove(Widget arg0) {
		throw new UnsupportedOperationException("not implemented");
	}

}
