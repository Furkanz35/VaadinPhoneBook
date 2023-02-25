package com.linxa.phonebook.ui;

import com.linxa.phonebook.domainobject.Contact;
import com.linxa.phonebook.domainobject.Person;
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
    Grid<Person> grid = new Grid<>();
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
                form.person = gridPersonComponentValueChangeEvent.getValue();
                form.getBinder().readBean(gridPersonComponentValueChangeEvent.getValue());
                form.displayPersonForm();
            }
            else
                form.closeForm();
        });
        form.save.addClickListener(event -> updateList(filterName.getValue(), filterNumber.getValue()));
        form.cancel.addClickListener(event -> {
                form.closeForm();
                form.person = null;
                });
        form.delete.addClickListener(event -> updateList(filterName.getValue(), filterNumber.getValue()));
        addContactButton.addClickListener(event -> {
            form.person = null;
            form.clearForm();
            form.displayPersonForm();
        });
        filterNumber.addValueChangeListener(e -> updateListByNumberFilter(filterNumber.getValue()));
        filterName.addValueChangeListener(e -> updateListByNameFilter(filterName.getValue()));
    }

    private void configureForm() {
        form.setWidth("30em");
        form.setVisible(false);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.addColumn(Person::getName).setHeader("Full Name").setSortable(true);
        grid.addColumn(Person::getPhoneNumber).setHeader("Phone Number").setSortable(true);
        grid.addColumn(Person::getEmailAddress).setHeader("Email Address").setSortable(true);
        grid.addColumn(Person::getCountry).setHeader("Country").setSortable(true);
        grid.addColumn(Person::getCity).setHeader("City").setSortable(true);
        grid.addColumn(Person::getStreet).setHeader("Street").setSortable(true);
        grid.setWidth("30em");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
    private void updateListByNameFilter(String value) {
        grid.setItems(Contact.getNameFilteredContactList(value));
    }
    private void updateListByNumberFilter(String value) {
        grid.setItems(Contact.getNumberFilteredContactList(value));
    }

    private void updateList(String nameFilter, String numberFilter) {
        updateListByNumberFilter(numberFilter);
        updateListByNameFilter(nameFilter);
    }

}