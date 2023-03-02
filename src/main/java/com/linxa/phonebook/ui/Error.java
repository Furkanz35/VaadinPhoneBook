package com.linxa.phonebook.ui;

public enum Error {
    DUPLICATE_NUMBER("There is another user with this number, number field should be unique"),
    EMPTY_NUMBER_OR_NAME("Name and Number can not be empty"),
    DATABASE_STATEMENT_CLOSE_PROBLEM("Statement close problem"),
    DATABASE_CONNECTION_CLOSE_PROBLEM("The error was occurred during closing connection of Database"),
    DATABASE_CONNECTION_OPEN_PROBLEM("The error was occurred during establish connection to Database"),
    DATABASE_ADDITION_PROBLEM("Related contact could not be added to DataBase"),
    DATABASE_DELETION_PROBLEM("Related contact could not be deleted from DataBase"),
    DATABASE_UPDATE_PROBLEM("Related contact could not be updated on DataBase")

    ;


    private final String error;

    public String getError() {
        return error;
    }

    Error(String description) {
        this.error = description;
    }
}
