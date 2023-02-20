package com.linxa.phonebook;

public class GreetService {

    public String greet(String str) {
        if(str == null)
            return "Welcome Furkan";
        return "Welcome" + str;
    }

}
