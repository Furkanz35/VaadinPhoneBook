package com.linxa.phonebook.ui;


import com.linxa.phonebook.domainobject.Contact;
import com.linxa.phonebook.domainobject.Person;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Objects;

@Route(value = "")
@PageTitle("PhoneBook")
public class ListView extends VerticalLayout {
    Grid<Person> grid = new Grid<>(Person.class);
    TextField filterName = new TextField();
    TextField filterNumber = new TextField();
    ContactForm form;

    public ListView() {
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(
                getToolbar(),
                getContent()
        );

        updateList(filterName.getValue(), filterNumber.getValue());
        grid.asSingleSelect().addValueChangeListener(event -> {
                    if (event.getValue() == null)
                        form.closeForm();
                    else {
                        form.displayPersonForm(event.getValue());
                    }
                }
        );

        form.save.addClickListener(event -> updateList(filterName.getValue(), filterNumber.getValue()));
        form.cancel.addClickListener(event -> form.closeForm());
        form.delete.addClickListener(event -> updateList(filterName.getValue(), filterNumber.getValue()));

//        updateListByNameFilter(filterName.getValue());
    }

    private void updateListByNameFilter(String value) {
            filterNumber.setValue("");
            grid.setItems(Contact.getNameFilteredContactList(value));

    }
    private void updateListByNumberFilter(String value) {
            filterName.setValue("");
            grid.setItems(Contact.getNumberFilteredContactList(value));
    }

    private void updateList(String nameFilter, String numberFilter) {
            updateListByNumberFilter(numberFilter);
            updateListByNameFilter(nameFilter);
    }

    private Component getContent() {
        HorizontalLayout content =  new HorizontalLayout(grid, form);
//        content.setFlexGrow(4, grid);
//        content.setFlexGrow(3, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        form = new ContactForm();
        form.setWidth("30em");
        form.setVisible(false);
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("name", "phoneNumber", "emailAddress");
        grid.addColumn(Person::getCountry).setHeader("Country");
        grid.addColumn(Person::getCity).setHeader("City");
        grid.addColumn(Person::getStreet).setHeader("Street");
        grid.setWidth("30em");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
        filterName.setPlaceholder("Filter by name:");
        filterName.setClearButtonVisible(true);
        filterName.setValueChangeMode(ValueChangeMode.EAGER);
        filterName.addValueChangeListener(e -> updateListByNameFilter(filterName.getValue()));

        filterNumber.setPlaceholder("Filter by number:");
        filterNumber.setClearButtonVisible(true);
        filterNumber.setValueChangeMode(ValueChangeMode.EAGER);
        filterNumber.addValueChangeListener(e -> updateListByNumberFilter(filterNumber.getValue()));


        Button addContactButton = new Button("Add contact");
        addContactButton.addClickListener(event -> {
            form.person = null;
            form.displayPersonForm();
        });

        HorizontalLayout toolbar = new HorizontalLayout(filterName, filterNumber, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }
}