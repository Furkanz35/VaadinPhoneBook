package com.linxa.phonebook.ui;

import com.linxa.phonebook.bookmanager.PhoneBookManager;
import com.linxa.phonebook.bookmanager.RecordBookManager;
import com.linxa.phonebook.domainobject.Contact;
import com.linxa.phonebook.domainobject.Person;
import com.linxa.phonebook.factory.PhoneBookManagerFactory;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;



public class ContactForm  extends FormLayout {

    Person person;

    public Person getPerson() {
        return person;
    }

    public void displayPersonForm(Person person){
        setPerson(person);
        setVisible(true);
    }

    public void displayPersonForm(){
        name.clear();
        emailAddress.clear();
        phoneNumber.clear();
        country.clear();
        city.clear();
        street.clear();
        setVisible(true);
    }


    public void closeForm(){
        name.clear();
        emailAddress.clear();
        phoneNumber.clear();
        country.clear();
        city.clear();
        street.clear();
        setVisible(false);
    }

    public void setPerson(Person person) {
        this.person = person;
        name.setValue(person.getName());
        emailAddress.setValue(person.getEmailAddress());
        phoneNumber.setValue(person.getPhoneNumber());
        country.setValue(person.getCountry());
        city.setValue(person.getCity());
        street.setValue(person.getStreet());
        System.err.println( person.getName() + " was displayed on form");

    }

    TextField name = new TextField("Name");
    TextField phoneNumber = new TextField("Phone Number");
    TextField emailAddress = new TextField("Email");
    TextField country = new TextField("Country");
    TextField city = new TextField("City");
    TextField street = new TextField("Street");
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
                city,
                street,
                createButtonLayout()
        );

    }

    private void deleteSelectedContact(Person person) {
        PhoneBookManagerFactory.getPhoneBookManager().deleteFromRecordBook(person);
        Notification.show(person.getName() + " was removed from Contact List", 5000, Notification.Position.BOTTOM_CENTER);
    }
    private void editExistingPersonOnContact(Person person) {
        Boolean flag = PhoneBookManagerFactory.getPhoneBookManager().editRecord(this.person, person);
        if(!flag){
            Notification.show("Name and Number field can not be empty", 3000, Notification.Position.MIDDLE);
        }
        else
            Notification.show(   person.getName() + " was upgraded", 5000, Notification.Position.BOTTOM_CENTER);
    }
    private void addNewPersonToContact(Person person) {
        System.out.println("Before addition map size --> " + Contact.contactMap.size());
        System.out.println("Before addition list size --> " + Contact.contactList.size());
        System.out.println("Before addition set size --> " + Contact.contactNumbersSet.size());

        Boolean flag = PhoneBookManagerFactory.getPhoneBookManager().addToRecordBook(person);
        if(flag) {
            Notification.show(person.getName()+ " was added to Contact List", 5000, Notification.Position.BOTTOM_CENTER);
        }
        else {
            Notification.show("Name and Number field can not be empty", 3000, Notification.Position.MIDDLE);
        }
        System.out.println("----- NEW PERSON--------");
        System.out.println(person.getName() + " was added to Contacts.");

        System.out.println("After addition map size --> " + Contact.contactMap.size());
        System.out.println("After addition list size --> " + Contact.contactList.size());
        System.out.println("After addition set size --> " + Contact.contactNumbersSet.size());
        closeForm();

    }

    private Component createButtonLayout() {
        name.setRequired(true);
        name.setHelperText("Name part can not be empty");
        phoneNumber.setRequired(true);
        phoneNumber.setHelperText("Number part can not be empty");

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        delete.addClickListener(event -> {
            if(person == null)
                closeForm();
            else {
                deleteSelectedContact(person);
            }
        });
        cancel.addClickListener(event -> closeForm());
        save.addClickListener(event -> {
            if(person == null) {
                addNewPersonToContact( new Person(
                        name.getValue(),
                        phoneNumber.getValue(),
                        emailAddress.getValue(),
                        country.getValue(),
                        street.getValue(),
                        city.getValue()
                        )
                );
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
        });

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.CANCEL);
        return new HorizontalLayout(save, delete, cancel);

    }



}
