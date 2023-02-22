package com.linxa.phonebook.bookmanager;


import com.linxa.phonebook.domainobject.Person;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;


public interface RecordBookManager {

    public  Boolean addToRecordBook(Person person);

    public  void deleteFromRecordBook(Person person);

    public Boolean editRecord(Person personToBeEdited , Person personUpgraded);


}
