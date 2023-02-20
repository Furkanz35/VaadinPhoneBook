package com.linxa.phonebook.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;

import java.awt.*;

public class ContactForm  extends FormLayout {

    TextField name = new TextField("Name");
    TextField phoneNumber = new TextField("Phone Number");
    EmailField emailAddress = new EmailField("Email");
    TextField country = new TextField("Country");
    TextField street = new TextField("Street");
    TextField city = new TextField("City");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    public  ContactForm() {
        addClassName("contact-form");
        add(
                name,
                phoneNumber,
                emailAddress,
                country,
                street,
                city,
                createButtonLayout()
        );
    }



    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.CANCEL);
        return new HorizontalLayout(save, delete, cancel);

    }



}
