package com.linxa.phonebook.bookmanager;

import com.linxa.phonebook.domainobject.Person;
public interface RecordBookManager {

    boolean addToRecordBook(Person person);

    void deleteFromRecordBook(Person person);

    int editRecord(Person personToBeEdited , Person personUpgraded);


}
