package com.linxa.phonebook.bookmanager;

import com.linxa.phonebook.database.ContactsDataBase;
import com.linxa.phonebook.domainobject.ContactStorage;
import com.linxa.phonebook.domainobject.Contact;
import com.linxa.phonebook.exception.ContactEditException;
import com.linxa.phonebook.ui.Error;

public class PhoneBookManager implements RecordBookManager{


    @Override
    public void addToRecordBook(Contact contact) throws ContactEditException {
        if(ContactsDataBase.createContactToDataBase(contact)) {
            ContactStorage.getContactMap().putIfAbsent(contact.getPhoneNumber(), contact);
        }
        else {
            System.err.println(Error.DATABASE_DELETION_PROBLEM);
            throw new ContactEditException("The addition process could not be performed, " +
                    "because the situation was occurred during contact addition to Database");
        }
    }

    @Override
    public void deleteFromRecordBook(Contact contact) throws ContactEditException {
        if(ContactsDataBase.deleteContactFromDataBase(contact)) {
            ContactStorage.getContactMap().remove(contact.getPhoneNumber(), contact);
        }
        else {
            System.err.println(Error.DATABASE_ADDITION_PROBLEM);
            throw new ContactEditException("The deletion process could not be performed, " +
                    "because the situation was occurred during contact deletion from Database");
        }
    }

    @Override
    public void editRecordWithSameNumber(Contact contactToBeEdited, Contact contactUpgraded) throws ContactEditException {
        if(ContactsDataBase.editContactFromDataBase(contactToBeEdited, contactUpgraded)) {
            ContactStorage.getContactMap().put(contactUpgraded.getPhoneNumber(), contactUpgraded);
        }
        else {
            System.err.println(Error.DATABASE_UPDATE_PROBLEM);
            throw new ContactEditException("The update process could not be performed, " +
                    "because the situation was occurred during contact update process on Database");
        }
   }

   @Override
    public void editRecordWithDifferentNumber(Contact contactToBeEdited, Contact contactUpgraded) throws ContactEditException {
       if(ContactsDataBase.editContactFromDataBase(contactToBeEdited, contactUpgraded)) {
          deleteContactFromCache(contactToBeEdited);
          addContactToCache(contactUpgraded);
       }
       else {
           System.err.println(Error.DATABASE_UPDATE_PROBLEM);
           throw new ContactEditException("The update process could not be performed, " +
                   "because the situation was occurred during contact update process on Database");
       }
    }
    public void addContactToCache(Contact contact) {
        ContactStorage.getContactMap().putIfAbsent(contact.getPhoneNumber(), contact);
    }

    public void deleteContactFromCache(Contact contact) {
        ContactStorage.getContactMap().remove(contact.getPhoneNumber(), contact);
    }

    public boolean isTheNumberUnique(Contact contact) {
        return !ContactStorage.getContactMap().containsKey(contact.getPhoneNumber());
    }
}
