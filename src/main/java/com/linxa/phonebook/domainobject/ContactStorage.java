package com.linxa.phonebook.domainobject;

import com.linxa.phonebook.database.ContactsDataBase;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ContactStorage {

    private static final ConcurrentHashMap<String, Contact> contactMap = new ConcurrentHashMap<>();

    static
    {
        try {
            ContactsDataBase.readContactsFromDataBase();
        } catch (SQLException e) {
            System.err.println("Contact information could not be retrieved from the database!!!");
            e.getCause();
            e.printStackTrace();
        }
    }

    public static List<Contact> getNumberandNameFilteredContactList(List<Contact> nameFilteredContactList, String filter) {
        return nameFilteredContactList.stream().filter(p -> p.getPhoneNumber().contains(filter)).collect(Collectors.toList());
    }

    public static List<Contact> getNameFilteredContactList(String filter) {
        return ContactStorage.getContactList().stream().filter(
                p -> p.getName()
                        .toLowerCase()
                        .contains(filter.toLowerCase()))
                .collect(Collectors.toList());
    }

    public static ConcurrentHashMap<String, Contact> getContactMap() {
        return contactMap;
    }

    public static List<Contact> getContactList() {
        List<Contact> list = new ArrayList<>(getContactMap().values());
        list.sort((Comparator.comparing(o -> o.getName().toLowerCase())));
        return list;
    }

}
