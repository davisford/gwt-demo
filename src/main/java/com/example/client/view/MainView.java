package com.example.client.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.client.model.Item;
import com.example.client.presenter.MainPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

/**
 * Main application view
 */
public class MainView extends ResizeComposite implements MainPresenter.Display {

	private static MainViewUiBinder uiBinder = GWT.create(MainViewUiBinder.class);

	interface MainViewUiBinder extends UiBinder<Widget, MainView> { }

	/**
	 * Interface for styling a row when it is selected
	 */
	interface SelectionStyle extends CssResource {
		String selectedRow();
	}

	/**
	 * Interface for ringing a callback to let them know when an item is selected
	 */
	public interface SelectedItemListener {
		void onSelectedItem(Item item);
	}

	@UiField SelectionStyle selectionStyle;

	@UiField Label nameLabel;

	@UiField Hyperlink logoutLink;

	@UiField FlexTable header;

	@UiField FlexTable table;

	@UiField Button refreshButton;

	@UiField Button deleteButton;

	@UiField Button newButton;

	@UiField Label errorLabel;

	// Single instance of the listener we need to tell when an item is selected
	private SelectedItemListener listener;

	// map of items indexed by row number
	private Map<Integer, Item> itemMap = new HashMap<Integer, Item>();

	// tracks which row is selected
	private int selectedRow = -1;

	/**
	 * Constructor
	 */
	public MainView() {
		initWidget(uiBinder.createAndBindUi(this));
		initTable();
	}

	/**
	 * Initializes table header, etc.
	 */
	private void initTable() {
		header.getColumnFormatter().setWidth(0, "128px");
		header.getColumnFormatter().setWidth(1, "192px");
		header.getColumnFormatter().setWidth(2, "256px");
		header.getColumnFormatter().setWidth(3, "25px");

		header.setText(0, 0, "Name");
		header.setText(0, 1, "Description");
		header.setText(0, 2, "Date");
		// checkbox column
		header.setText(0, 3, " ");

		table.getColumnFormatter().setWidth(0, "128px");
		table.getColumnFormatter().setWidth(1, "192px");
		table.getColumnFormatter().setWidth(2, "256px");
		table.getColumnFormatter().setWidth(3, "25px");
	}

	/**
	 * Handler that runs when the user clicks on the table
	 * @param event
	 */
	@UiHandler("table")
	void onTableClicked(ClickEvent event) {
		// Select the row that was clicked (-1 to account for header row).
		Cell cell = table.getCellForEvent(event);
		if (cell != null) {
			int row = cell.getRowIndex();
			if(cell.getCellIndex() != 3) {
				// don't select if checkbox is ticked
				selectRow(row);
			}
		}
	}

	/**
	 * Select a particular row
	 * @param row
	 */
	private void selectRow(int row) {
		// remove styling from previously selected row
		styleRow(selectedRow, false);
		// add styling to newly selected row
		styleRow(row, true);

		selectedRow = row;
		if (listener != null) {
			listener.onSelectedItem(itemMap.get(row));
		}
	}

	/**
	 * Styles a particular row if it is selected
	 * @param row the row 
	 * @param selected whether it is selected 
	 */
	private void styleRow(int row, boolean selected) {
		if (row != -1) {
			String style = selectionStyle.selectedRow();

			if (selected) {
				table.getRowFormatter().addStyleName(row, style);
			} else {
				table.getRowFormatter().removeStyleName(row, style);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.example.client.presenter.WidgetDisplay#asWidget()
	 */
	@Override
	public Widget asWidget() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.example.client.presenter.MainPresenter.Display#setNameText(java.lang.String)
	 */
	@Override
	public void setNameText(String text) {
		nameLabel.setText(text);
	}

	/*
	 * (non-Javadoc)
	 * @see com.example.client.presenter.MainPresenter.Display#logoutLink()
	 */
	@Override
	public HasClickHandlers logoutLink() {
		return logoutLink;
	}

	/*
	 * (non-Javadoc)
	 * @see com.example.client.presenter.MainPresenter.Display#delete()
	 */
	@Override
	public HasClickHandlers delete() {
		return deleteButton;
	}

	/*
	 * (non-Javadoc)
	 * @see com.example.client.presenter.MainPresenter.Display#newItemClick()
	 */
	@Override
	public HasClickHandlers newItemClick() {
		return newButton;
	}

	/*
	 * (non-Javadoc)
	 * @see com.example.client.presenter.MainPresenter.Display#refresh()
	 */
	@Override
	public HasClickHandlers refresh() {
		return refreshButton;
	}

	/*
	 * (non-Javadoc)
	 * @see com.example.client.presenter.MainPresenter.Display#setItems(java.util.ArrayList)
	 */
	@Override
	public void setItems(ArrayList<Item> items) {
		itemMap.clear();
		table.removeAllRows();
		initTable();

		for (int i = 0; i < items.size(); ++i) {
			final Item item = items.get(i);
			table.setText(i, 0, item.getName());
			table.setText(i, 1, item.getDescription());
			table.setText(i, 2, item.getDate().toString());
			table.setWidget(i, 3, new CheckBox());

			// add to item map
			itemMap.put(i, item);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.example.client.presenter.MainPresenter.Display#setErrorMsg(java.lang.String)
	 */
	@Override
	public void setErrorMsg(String msg) {
		errorLabel.setText(msg);
	}

	/*
	 * (non-Javadoc)
	 * @see com.example.client.presenter.MainPresenter.Display#getSelectedItems()
	 */
	@Override
	public ArrayList<Item> getSelectedItems() {
		final ArrayList<Item> list = new ArrayList<Item>();
		for (int i = 0; i < table.getRowCount(); i++) {
			CheckBox cb = (CheckBox) table.getWidget(i, 3);
			if (cb.getValue()) {
				list.add(itemMap.get(i));
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * @see com.example.client.presenter.MainPresenter.Display#setSelectedItemListener(com.example.client.view.MainView.SelectedItemListener)
	 */
	@Override
	public void setSelectedItemListener(SelectedItemListener listener) {
		this.listener = listener;
	}

}
