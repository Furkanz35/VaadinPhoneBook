package com.linxa.phonebook.bookmanager;

import com.linxa.phonebook.domainobject.Contact;
import com.linxa.phonebook.domainobject.Person;

public class PhoneBookManager implements RecordBookManager{
    @Override
    public boolean addToRecordBook(Person person) {
        if(person.getPhoneNumber().isEmpty() || person.getName().isEmpty()) {
            System.err.println("Name and Number can not be empty");
            return false;
        }
        boolean flag = Contact.getContactMap().putIfAbsent(person.getPhoneNumber(), person) == null;
        if(flag) {
            System.out.println("New person was added to Contacts ---> " + person.getName());
        }
        else {
            System.err.println("There is already a contact with this number --> " + person.getPhoneNumber());
        }
        return flag;
    }

    @Override
    public void deleteFromRecordBook(Person person) {
            Contact.getContactMap().remove(person.getPhoneNumber(), person);
            System.out.println("Selected contact was removed ->" + person.getName());
    }

    @Override
    public int editRecord(Person personToBeEdited ,Person personUpgraded) {
        if(personUpgraded.getPhoneNumber().isEmpty() || personUpgraded.getName().isEmpty()) {
            System.err.println("Name and Number can not be empty");
            return 0;
        }
            if (personToBeEdited.getPhoneNumber().equals(personUpgraded.getPhoneNumber())) {
                Contact.getContactMap().put(personUpgraded.getPhoneNumber(), personUpgraded);
                return 1;
            } else if (isTheNumberUnique(personUpgraded)) {
                deleteFromRecordBook(personToBeEdited);
                addToRecordBook(personUpgraded);
                return 1;
            } else {
              return 2;
            }
    }
    private Boolean isTheNumberUnique(Person person) {
        return !Contact.getContactMap().containsKey(person.getPhoneNumber());
    }
}
