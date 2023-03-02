package com.linxa.phonebook.bookmanager;

import com.linxa.phonebook.domainobject.Contact;


public interface RecordBookManager {

    void addToRecordBook(Contact contact) throws Exception;

    void deleteFromRecordBook(Contact contact) throws Exception;

    void editRecordWithSameNumber(Contact contactToBeEdited, Contact contactUpgraded) throws Exception;

    void editRecordWithDifferentNumber(Contact contactToBeEdited, Contact contactUpgraded) throws Exception;


}
