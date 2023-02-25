package com.linxa.phonebook.ui;

import com.linxa.phonebook.domainobject.Person;
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

    private final Binder<Person> binder = new Binder<>(Person.class);
    TextField name = new TextField("Full Name");
    TextField phoneNumber = new TextField("Phone Number");
    TextField emailAddress = new TextField("Email");
    TextField country = new TextField("Country");
    TextField city = new TextField("City");
    TextField street = new TextField("Street");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    Person person;

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
                .bind(Person::getName, Person::setName);
        getBinder().forField(phoneNumber).asRequired("Number area can not be empty")
                .bind(Person::getPhoneNumber, Person::setPhoneNumber);
        getBinder().forField(emailAddress).bind(Person::getEmailAddress, Person::setEmailAddress);
        getBinder().forField(country).bind(Person::getCountry, Person::setCountry);
        getBinder().forField(city).bind(Person::getCity, Person::setCity);
        getBinder().forField(street).bind(Person::getStreet, Person::setStreet);

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

    private void deleteSelectedContact(Person person) {
        PhoneBookManagerFactory.getPhoneBookManager().deleteFromRecordBook(person);
        Notification.show(person.getName() + " was removed from Contact List", 5000, Notification.Position.BOTTOM_CENTER);
    }

    private void editExistingPersonOnContact(Person person) {
        int flag = PhoneBookManagerFactory.getPhoneBookManager().editRecord(this.person, person);
        if(flag == 0){
            Notification.show("Name and Number field can not be empty", 3000, Notification.Position.MIDDLE);
        } else if (flag == 1) {
            Notification.show(   person.getName() + " was upgraded", 5000, Notification.Position.BOTTOM_CENTER);
        } else {
            Notification.show("There is another user with this number, number field should be unique",
                    5000, Notification.Position.MIDDLE);
        }
    }

    private void addNewPersonToContact(Person person) {
        boolean flag = PhoneBookManagerFactory.getPhoneBookManager().addToRecordBook(person);
        if(flag) {
            Notification.show(person.getName()+ " was added to Contact List", 5000, Notification.Position.BOTTOM_CENTER);
        }
        else {
            Notification.show("Name and Number field can not be empty", 3000, Notification.Position.MIDDLE);
        }
        closeForm();

    }

    private void setFormListeners(){
        delete.addClickListener(event -> {
            if(person == null)
                closeForm();
            else {
                System.out.println(person.getName() + " was deleted.");
                deleteSelectedContact(person);
            }
        });


        cancel.addClickListener(event -> {
            person = null;
            closeForm();
        });


        save.addClickListener(event -> {
            if(person == null) {
                person = new Person();
                try {
                    binder.writeBean(person);
                    addNewPersonToContact(person);
                } catch (ValidationException e) {
                    e.printStackTrace();
                    Notification.show(
                            "Name and Number fields must be filled during addition",
                            5000, Notification.Position.MIDDLE);
                }
            }
            else {
                editExistingPersonOnContact( new Person(
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

    public Binder<Person> getBinder() {
        return binder;
    }

}
