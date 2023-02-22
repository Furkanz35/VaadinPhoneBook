package com.linxa.phonebook.bookmanager;

import com.linxa.phonebook.domainobject.Contact;
import com.linxa.phonebook.domainobject.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PhoneBookManager implements RecordBookManager{



    @Override
    public Boolean addToRecordBook(Person person) {
        if(person.getPhoneNumber().isEmpty() || person.getName().isEmpty()) {
            System.err.println("Name and Number can not be empty");
            return false;
        }
        Boolean flag = Contact.getContactMap().putIfAbsent(person.getPhoneNumber(), person) == null;
        if(flag) {
            System.out.println("New person was added to Contacts ---> " + person.getName());
        }
        else {
            System.err.println("There is already a contact with this number --> " + person.getPhoneNumber());
        }
        Contact.updateContactList();
        return flag;
    }

    private Boolean isTheNumberUnique(Person person) {
        return !Contact.contactMap.containsKey(person.getPhoneNumber());
    }

    @Override
    public void deleteFromRecordBook(Person person) {
            Contact.getContactMap().remove(person.getPhoneNumber(), person);
            System.out.println("Selected contact was removed ->" + person.getName());
            Contact.updateContactList();
    }

    @Override
    public Boolean editRecord(Person personToBeEdited ,Person personUpgraded) {
        if(personUpgraded.getPhoneNumber().isEmpty() || personUpgraded.getName().isEmpty()) {
            System.err.println("Name and Number can not be empty");
            return false;
        }
        {
            System.out.println("Test result -> " + personToBeEdited.getPhoneNumber().equals(personUpgraded.getPhoneNumber()));
            System.out.println(personToBeEdited);
            System.out.println(personUpgraded);

            if (personToBeEdited.getPhoneNumber().equals(personUpgraded.getPhoneNumber())) {
                Contact.getContactMap().put(personUpgraded.getPhoneNumber(), personUpgraded);
                Contact.updateContactList();
            } else if (isTheNumberUnique(personUpgraded)) {
                deleteFromRecordBook(personToBeEdited);
                addToRecordBook(personUpgraded);
            } else {
              return false;
            }

            Contact.contactMap.forEach((map, val) -> System.out.println(val.toString()));
            return true;
        }

    }
}
