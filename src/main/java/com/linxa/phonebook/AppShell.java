package com.linxa.phonebook;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;

/**
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@PWA(name = "Phone Book for Vaadin", shortName = "PhoneBook")
public class AppShell implements AppShellConfigurator {
}
