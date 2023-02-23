package com.linxa.phonebook.domainobject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Contact {

    private static final ConcurrentHashMap<String, Person> contactMap = new ConcurrentHashMap<>();

    static
    {
        Contact.contactMap.put("5069732374", new Person("Bilal Zengin", "5069732374","bilal@linxa.com",
                "Turkey", "Karşıyakalılar", "İzmir" ));
        Contact.contactMap.put("5312016858", new Person("Nesrin Imre", "5312016858", "nesrin@gmail.com"));
        Contact.contactMap.put("+32941312412", new Person("Erdoğan Çanakçı", "+32941312412", "erdogan@linxa.com"));
        Contact.contactMap.put("21312341231", new Person("Selim Acar", "21312341231", "sdasda@gmail.com"));
        Contact.contactMap.put("0513213023", new Person("Fatih Enes", "0513213023", "fatih@hitit.com",
                "France", "Rue de l'Abreuvoir", "Paris"));
        Contact.contactMap.put("2141234123", new Person("Emre Hankapısı", "2141234123"));
        Contact.contactMap.put("6653624525234", new Person("Zeytin Adiguzel", "6653624525234", "zeytin@gmail.com"));
        Contact.contactMap.put("423156123", new Person("Talha Yurtsever", "423156123"));
        Contact.contactMap.put("983247234123", new Person("Kardelen Enes", "983247234123"));

    }

    public static List<Person> getNumberFilteredContactList(String filter) {
        return Contact.getContactList().stream().filter(p -> p.getPhoneNumber().toLowerCase().contains(filter.toLowerCase())).collect(Collectors.toList());
    }
    public static List<Person> getNameFilteredContactList(String filter) {
        return Contact.getContactList().stream().filter(p -> p.getName().toLowerCase().contains(filter.toLowerCase())).collect(Collectors.toList());
    }

    public static Set<String> getContactNumbersSet() {
        return contactMap.keySet();
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
