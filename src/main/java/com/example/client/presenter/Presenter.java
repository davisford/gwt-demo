package com.example.client.presenter;

import com.google.gwt.user.client.ui.HasWidgets;

/**
 * Interface for all presenters
 */
public interface Presenter {

	/**
	 * Instructs the presenter to update the {@link HasWidgets} <tt>container</tt> with it's own display
	 * @param container the container to use as a parent
	 */
	void go(final HasWidgets container);
}
