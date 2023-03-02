package com.linxa.phonebook.ui;

import com.linxa.phonebook.domainobject.Contact;
import com.linxa.phonebook.exception.ContactEditException;
import com.linxa.phonebook.factory.PhoneBookManagerFactory;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;


public class ContactForm  extends FormLayout {

    private final Binder<Contact> binder = new Binder<>(Contact.class);
    TextField name = new TextField("Full Name");
    TextField phoneNumber = new TextField("Phone Number");
    TextField emailAddress = new TextField("Email");
    TextField country = new TextField("Country");
    TextField city = new TextField("City");
    TextField street = new TextField("Street");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    Contact contact;

    public  ContactForm() {
        add(
                name,
                phoneNumber,
                emailAddress,
                country,
                city,
                street,
                createButtonLayout()
        );

        setFormListeners();

        getBinder().forField(name).asRequired("Name area can not be empty")
                .bind(Contact::getName, Contact::setName);
        getBinder().forField(phoneNumber).asRequired("Number area can not be empty")
                .bind(Contact::getPhoneNumber, Contact::setPhoneNumber);
        getBinder().forField(emailAddress).bind(Contact::getEmailAddress, Contact::setEmailAddress);
        getBinder().forField(country).bind(Contact::getCountry, Contact::setCountry);
        getBinder().forField(city).bind(Contact::getCity, Contact::setCity);
        getBinder().forField(street).bind(Contact::getStreet, Contact::setStreet);

    }

    public void displayPersonForm(){
        setVisible(true);
    }

    public void clearForm(){
        name.clear();
        emailAddress.clear();
        phoneNumber.clear();
        country.clear();
        city.clear();
        street.clear();
    }

    public void closeForm(){
        setVisible(false);
    }

    private void deleteSelectedContact(Contact contact) {
        try {
            PhoneBookManagerFactory.getPhoneBookManager().deleteFromRecordBook(contact);
            Notification.show(contact.getName() + " was removed from Contact List", 5000, Notification.Position.BOTTOM_CENTER);
        }catch (ContactEditException e) {
            Notification.show(String.valueOf(Error.DATABASE_DELETION_PROBLEM), 5000, Notification.Position.BOTTOM_CENTER);
            e.printStackTrace();
        }
    }

    private void editExistingPersonOnContact(Contact contact) {

        if(PhoneBookManagerFactory.getPhoneBookManager().isTheNumberUnique(contact)) {
            try {
                PhoneBookManagerFactory.getPhoneBookManager().editRecordWithDifferentNumber(this.contact, contact);
                Notification.show(contact.getName() + " was upgraded", 5000, Notification.Position.BOTTOM_CENTER);
            } catch (ContactEditException e) {
                Notification.show(String.valueOf(Error.DATABASE_UPDATE_PROBLEM), 5000, Notification.Position.BOTTOM_CENTER);
                e.printStackTrace();
            }
        }
        else {
            if(this.contact.getPhoneNumber().equals(contact.getPhoneNumber())) {
                try {
                    PhoneBookManagerFactory.getPhoneBookManager().editRecordWithSameNumber(this.contact, contact);
                    Notification.show(   contact.getName() + " was upgraded", 5000, Notification.Position.BOTTOM_CENTER);
                } catch (ContactEditException e) {
                    Notification.show(String.valueOf(Error.DATABASE_UPDATE_PROBLEM), 5000, Notification.Position.BOTTOM_CENTER);
                    e.printStackTrace();
                }
            }
            else {
                Notification.show(Error.DUPLICATE_NUMBER.getError(),5000, Notification.Position.MIDDLE);
            }
        }
    }

    private void addNewPersonToContact(Contact contact) {
        if(PhoneBookManagerFactory.getPhoneBookManager().isTheNumberUnique(contact)){
            try {
                PhoneBookManagerFactory.getPhoneBookManager().addToRecordBook(contact);
                Notification.show(contact.getName()+ " was added to Contact List", 5000, Notification.Position.BOTTOM_CENTER);
            } catch (ContactEditException e) {
                Notification.show(String.valueOf(Error.DATABASE_ADDITION_PROBLEM), 5000, Notification.Position.BOTTOM_CENTER);
                e.printStackTrace();
            }

        }
        else {
            Notification.show(Error.DUPLICATE_NUMBER.getError(), 3000, Notification.Position.MIDDLE);
        }
        closeForm();
    }

    private void setFormListeners(){
        delete.addClickListener(event -> {
            if(contact == null)
                closeForm();
            else {
                System.out.println(contact.getName() + " was deleted.");
                deleteSelectedContact(contact);
            }
        });


        cancel.addClickListener(event -> {
            contact = null;
            closeForm();
        });


        save.addClickListener(event -> {
            if(contact == null) {
                contact = new Contact();
                try {
                    binder.writeBean(contact);
                    addNewPersonToContact(contact);
                } catch (ValidationException e) {
                    e.printStackTrace();
                    Notification.show(
                            Error.EMPTY_NUMBER_OR_NAME.getError(),
                            5000, Notification.Position.MIDDLE);
                }
            }
            else {
                editExistingPersonOnContact( new Contact(
                        name.getValue(),
                        phoneNumber.getValue(),
                        emailAddress.getValue(),
                        country.getValue(),
                        street.getValue(),
                        city.getValue()
                ));
            }
            closeForm();
        });

    }

    private Component createButtonLayout() {
        name.setRequired(true);
        name.setHelperText("Name part can not be empty");
        phoneNumber.setRequired(true);
        phoneNumber.setHelperText("Number part can not be empty");

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.CANCEL);
        return new HorizontalLayout(save, delete, cancel);
    }

    public Binder<Contact> getBinder() {
        return binder;
    }

}
