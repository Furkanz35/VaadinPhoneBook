package com.linxa.phonebook.domainobject;

import java.math.BigInteger;
import java.util.*;

public class Contact {
    public static Map<String, Person> contactMap = new HashMap<>();


    static
    {
        Contact.contactMap.put("5069732374", new Person("Furkan Zengin", "5069732374","furkan@linxa.com",
                "Turkey", "Karşıyaka", "İzmir" ));
        Contact.contactMap.put("5312016858", new Person("Melike Imre", "5312016858", "melikeimre@gmail.com"));
        Contact.contactMap.put("+32941312412", new Person("Erdoğan Çanakçı", "+32941312412", "erdogan@linxa.com"));
        Contact.contactMap.put("21312341231", new Person("Selim Acar", "21312341231", "sdasda@gmail.com"));
        Contact.contactMap.put("0513213023", new Person("Atıf Enes", "0513213023"));
        Contact.contactMap.put("0513213023", new Person("Fatih Enes", "0513213023", "fatih@hitit.com",
                "France", "Helvaciogli", "Paris"));
        Contact.contactMap.put("2141234123", new Person("Emre Karsi", "2141234123"));
        Contact.contactMap.put("6653624525234", new Person("Zeytin Adiguzel", "6653624525234", "zeytin@gmail.com"));
        Contact.contactMap.put("423156123", new Person("Fatih Yurtsever", "423156123"));
        Contact.contactMap.put("983247234123", new Person("Kardelen Enes", "983247234123"));

    }
    public static List<Person> contactList = new ArrayList<>(contactMap.values());

    public static Set<String> contactNumbersSet = contactMap.keySet();

    public static List<Person> getNumberFilteredContactList(String filter) {
        List<Person> filteredList = new ArrayList<>();
        for(Person number : getContactList()){
            if(number.getPhoneNumber().contains(filter))
                filteredList.add(Contact.getContactMap().get(number.getPhoneNumber()));
        }
        return filteredList;

    }

    public static void updateContactList(){
        contactList = new ArrayList<Person>(contactMap.values());
    }

    public static List<Person> getNameFilteredContactList(String filter) {
        List<Person> filteredList = new ArrayList<>();
        for(Person person : getContactList()) {
            if(person.getName().toLowerCase().contains(filter.toLowerCase()))
                filteredList.add(person);
        }
        return filteredList;
    }

    public static Set<String> getContactNumbersSet() {
        return contactNumbersSet;
    }

    public static void setContactMap(Map<String, Person> contactMap) {
        Contact.contactMap = contactMap;
    }

    public static void setContactList(List<Person> contactList) {
        Contact.contactList = contactList;
    }

    public static Map<String, Person> getContactMap() {
        return contactMap;
    }

    public static List<Person> getContactList() {

       contactList.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
       return contactList;
    }

}
