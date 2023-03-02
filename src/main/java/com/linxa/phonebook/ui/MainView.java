package com.linxa.phonebook.ui;

import com.linxa.phonebook.domainobject.Contact;
import com.linxa.phonebook.domainobject.ContactStorage;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "")
@PageTitle("PhoneBook")
public class MainView extends VerticalLayout {
    Grid<Contact> grid = new Grid<>();
    TextField filterName = new TextField();
    TextField filterNumber = new TextField();
    ContactForm form = new ContactForm();
    Button addContactButton = new Button("Add contact");

    public MainView() {
        setSizeFull();
        configureGrid();
        configureForm();
        add(
                getToolbar(),
                getContent()
        );
        setListeners();
        updateList(filterName.getValue(), filterNumber.getValue());
    }

    private Component getContent() {
        HorizontalLayout content =  new HorizontalLayout(grid, form);
        content.setSizeFull();
        return content;
    }

    private Component getToolbar() {
        filterName.setPlaceholder("Filter by name:");
        filterName.setClearButtonVisible(true);
        filterName.setValueChangeMode(ValueChangeMode.EAGER);

        filterNumber.setPlaceholder("Filter by number:");
        filterNumber.setClearButtonVisible(true);
        filterNumber.setValueChangeMode(ValueChangeMode.EAGER);

        return new HorizontalLayout(filterName, filterNumber, addContactButton);
    }

    private void setListeners() {
        grid.asSingleSelect().addValueChangeListener(gridPersonComponentValueChangeEvent -> {
            if(gridPersonComponentValueChangeEvent.getValue() != null) {
                form.contact = gridPersonComponentValueChangeEvent.getValue();
                form.getBinder().readBean(gridPersonComponentValueChangeEvent.getValue());
                form.displayPersonForm();
            }
            else
                form.closeForm();
        });
        form.save.addClickListener(event -> updateList(filterName.getValue(), filterNumber.getValue()));
        form.cancel.addClickListener(event -> {
                form.closeForm();
                form.contact = null;
                });
        form.delete.addClickListener(event -> updateList(filterName.getValue(), filterNumber.getValue()));
        addContactButton.addClickListener(event -> {
            form.contact = null;
            form.clearForm();
            form.displayPersonForm();
        });
        filterNumber.addValueChangeListener(e -> updateList(filterName.getValue(), filterNumber.getValue()));
        filterName.addValueChangeListener(e -> updateList(filterName.getValue(), filterNumber.getValue()));
    }

    private void configureForm() {
        form.setWidth("30em");
        form.setVisible(false);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.addColumn(Contact::getName).setHeader("Full Name").setSortable(true);
        grid.addColumn(Contact::getPhoneNumber).setHeader("Phone Number").setSortable(true);
        grid.addColumn(Contact::getEmailAddress).setHeader("Email Address").setSortable(true);
        grid.addColumn(Contact::getCountry).setHeader("Country").setSortable(true);
        grid.addColumn(Contact::getCity).setHeader("City").setSortable(true);
        grid.addColumn(Contact::getStreet).setHeader("Street").setSortable(true);
        grid.setWidth("30em");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void updateList(String nameFilter, String numberFilter) {
        grid.setItems(ContactStorage.getNumberandNameFilteredContactList(ContactStorage.getNameFilteredContactList(nameFilter), numberFilter));
    }

}