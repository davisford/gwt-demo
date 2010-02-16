package com.example.client.presenter;

import com.google.gwt.user.client.ui.Widget;

/**
 * Root interface for {@link Presenter} display interfaces
 */
public interface WidgetDisplay {
	
	/**
	 * Returns the view as a {@link Widget} so it can be added to anything that implements {@link HasWidgets}
	 * @return this
	 */
	Widget asWidget();
}
