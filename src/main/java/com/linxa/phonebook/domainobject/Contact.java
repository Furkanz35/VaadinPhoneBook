package com.linxa.phonebook.domainobject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Contact {

    private static final ConcurrentHashMap<String, Person> contactMap = new ConcurrentHashMap<>();

    static
    {
        Contact.contactMap.put("5069732374", new Person("Haluk Zen gin", "5069732374","becauseIamBatman@batman.com",
                "Turkey", "KSK", "İzmir" ));
        Contact.contactMap.put("5312014858", new Person("Sniffer Imre", "5312014858", "whysoserious@joker.com"));
        Contact.contactMap.put("+32941312412", new Person("Elif Mira Yank", "+32941312412", "goodMorningVietnam@linxa.com"));
        Contact.contactMap.put("21312341231", new Person("Alperen Scar", "21312341231", "whatisinthebooxx@seven.com"));
        Contact.contactMap.put("0513213023", new Person("Kismet Karma", "0513213023", "winteriscoming@got.com",
                "France", "Rue de l'Abreuvoir", "Paris"));
        Contact.contactMap.put("2141234123", new Person("Mustafa Hankering", "2141234123", "", "USA", "Miami", ""));
        Contact.contactMap.put("6653624525234", new Person("Beyza Digitalized", "6653624525234", "myPrecious@lotr.com", "England"));
        Contact.contactMap.put("423156123", new Person("Selman Alim", "423156123", "abracadabra@prestige.com"));
        Contact.contactMap.put("657123813", new Person("Ayse Zengin", "657123813", "youWereTheChosenOne@starwars.com"));
        Contact.contactMap.put("983247234123", new Person("Kardelen Enes", "983247234123"));

    }

    public static List<Person> getNumberFilteredContactList(String filter) {
        return Contact.getContactList().stream().filter(p -> p.getPhoneNumber().toLowerCase().contains(filter.toLowerCase())).collect(Collectors.toList());
    }

    public static List<Person> getNameFilteredContactList(String filter) {
        return Contact.getContactList().stream().filter(p -> p.getName().toLowerCase().contains(filter.toLowerCase())).collect(Collectors.toList());
    }

    public static ConcurrentHashMap<String, Person> getContactMap() {
        return contactMap;
    }

    public static List<Person> getContactList() {
        List<Person> list = new ArrayList<>(getContactMap().values());
        list.sort((Comparator.comparing(o -> o.getName().toLowerCase())));
        return list;
    }

}
