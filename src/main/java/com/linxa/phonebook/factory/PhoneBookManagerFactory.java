package com.linxa.phonebook.factory;

import com.linxa.phonebook.bookmanager.PhoneBookManager;

public class PhoneBookManagerFactory {

    private static final PhoneBookManager PHONE_BOOK_MANAGER;

    static
    {
        PHONE_BOOK_MANAGER = new PhoneBookManager();
    }

    public static PhoneBookManager getPhoneBookManager(){
        return PHONE_BOOK_MANAGER;
    }

}
