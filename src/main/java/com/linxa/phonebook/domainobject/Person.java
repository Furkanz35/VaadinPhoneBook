package com.linxa.phonebook.domainobject;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Objects;

public class Person {
    private String name;
    private String phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return phoneNumber.equals(person.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber);
    }

    private String emailAddress;
    private String country;
    private String street;
    private String city;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Person(String name, String phoneNumber, String emailAddress, String country, String street, String city) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.country = country;
        this.street = street;
        this.city = city;
    }

    public Person(String name, String phoneNumber, String emailAddress) {
        this(name, phoneNumber, emailAddress, "", "", "");
    }

    public Person(String name, String phoneNumber, String emailAddress, String country) {
        this(name, phoneNumber, emailAddress, country, "", "");
    }

    public Person(String name, String phoneNumber) {
        this(name, phoneNumber, "", "",  "", "");
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        if(null == phoneNumber)
            return false;

        return isNumeric(phoneNumber);
    }

/*    private boolean validateEmailAddress(String emailAddress) {
        boolean result = true;

        try {
            InternetAddress email = new InternetAddress(emailAddress);
            email.validate();
        } catch (AddressException e) {
            result = false;
        }
        return result;
    }


    private boolean isValidateContact(String phoneNumber, String emailAddress){
        if(validateEmailAddress(emailAddress) && validatePhoneNumber(phoneNumber))
    }*/

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
