/**
 * 
 */
package com.example.client.view;

import com.example.client.model.Item;
import com.example.client.presenter.ItemPresenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

/**
 * @author davisford
 *
 */
public class ItemView extends Composite implements ItemPresenter.Display {

    private TextBox nameBox = new TextBox();
	private TextBox descriptionBox = new TextBox();
	private DatePicker datePicker = new DatePicker();
	private Button saveButton = new Button("Save");
	private Button cancelButton = new Button("Cancel");
	
	private VerticalPanel vPanel = new VerticalPanel();
	private FlexTable table;
	private DialogBox dialogBox;
	
	private Item item;
	
	/**
	 * Constructor
	 * <p>
	 * Use if you want to create a new {@link Item}
	 */
	public ItemView() {
		item = new Item();

	    table = new FlexTable();
		table.setHTML(0, 0, "Name");
		table.setWidget(0, 1, nameBox);
		table.setHTML(1, 0, "Description");
		table.setWidget(1, 1, descriptionBox);
		table.setHTML(2, 0, "Date");
		table.setWidget(2, 1, datePicker);
		table.setWidget(3, 0, saveButton);
		table.setWidget(3, 1, cancelButton);
		
		table.setCellSpacing(4);
		
		vPanel.add(table);
		
		initWidget(vPanel);
	}
	
	@Override
	public Item getItem() {
		item.setName(nameBox.getText());
		item.setDescription(descriptionBox.getText());
		item.setDate(datePicker.getValue());
		return item;
	}
	
	@Override
	public HasClickHandlers save() {
		return saveButton;
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasClickHandlers cancel() {
		return cancelButton;
	}

	@Override
	public void removePopUp() {
		dialogBox.hide();
	}

	@Override
	public void showPopUp(Item item) {
		this.item = item;
		nameBox.setText(item.getName());
		descriptionBox.setText(item.getDescription());
		datePicker.setCurrentMonth(item.getDate());
		datePicker.setValue(item.getDate());
		dialogBox = new DialogBox();
		dialogBox.setText("Create/Edit an Item");
		dialogBox.setWidget(this);
		dialogBox.setGlassEnabled(true);
		dialogBox.setAnimationEnabled(true);
		dialogBox.center();
		dialogBox.show();
	}

}
