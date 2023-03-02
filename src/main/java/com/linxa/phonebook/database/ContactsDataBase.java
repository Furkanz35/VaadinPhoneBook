package com.linxa.phonebook.database;

import com.linxa.phonebook.domainobject.Contact;
import com.linxa.phonebook.factory.PhoneBookManagerFactory;
import com.linxa.phonebook.ui.Error;

import java.sql.*;


public class ContactsDataBase {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/phonebook_application";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "lxMac34!2";

    public static void readContactsFromDataBase() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(
                    DATABASE_URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM contacts");
            System.out.println("--------- DB READ LOG-------------");
            while (resultSet.next()) {
                PhoneBookManagerFactory.getPhoneBookManager().addContactToCache(new Contact(
                        resultSet.getString("name"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email_address"),
                        resultSet.getString("country"),
                        resultSet.getString("city"),
                        resultSet.getString("street")

                ));
            }
        } catch (SQLException | ClassNotFoundException sqlEx) {
            System.err.println(Error.DATABASE_CONNECTION_OPEN_PROBLEM);
            sqlEx.printStackTrace();
        } finally {
            closeConnectionAndStatement(connection, statement);

        }
    }
    public static boolean createContactToDataBase(Contact newContact){
        int updateCount ;
        boolean flag = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement("INSERT INTO contacts " +
                    "VALUES(default,?,?,?,?,?,?)");
            preparedStatement.setString(1, newContact.getPhoneNumber());
            preparedStatement.setString(2, newContact.getName());
            preparedStatement.setString(3, newContact.getEmailAddress());
            preparedStatement.setString(4, newContact.getCountry());
            preparedStatement.setString(5, newContact.getCity());
            preparedStatement.setString(6, newContact.getStreet());

            updateCount = preparedStatement.executeUpdate();
            if (updateCount == 1) {
                flag = true;
            }
        }catch (SQLException | ClassNotFoundException sqlEx) {
            System.err.println(Error.DATABASE_CONNECTION_OPEN_PROBLEM);
            sqlEx.printStackTrace();
        } finally {
            closeConnectionAndStatement(connection, preparedStatement);
        }
        return flag;
    }

    public static boolean deleteContactFromDataBase(Contact contact) {
        int deleteCount;
        boolean flag = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int contactId = 0;
        try {
                Class.forName(JDBC_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
                preparedStatement = connection.prepareStatement("SELECT * FROM contacts WHERE phone_number = ?" );
                preparedStatement.setString(1, contact.getPhoneNumber());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next())
                    contactId = resultSet.getInt("int_key");
                if (contactId == 0) {
                    System.err.println("There is a problem during deletion (related Contact could not be found with int_key value of Database table");
                    return false;
                }
                preparedStatement = connection.prepareStatement("DELETE FROM contacts WHERE int_key = ?");
                preparedStatement.setInt(1, contactId);
                deleteCount = preparedStatement.executeUpdate();
                if (deleteCount == 1) {
                    flag = true;
                }
            } catch (SQLException | ClassNotFoundException sqlEx) {
                System.err.println(Error.DATABASE_CONNECTION_OPEN_PROBLEM);
                sqlEx.printStackTrace();
            } finally {
                closeConnectionAndStatement(connection, preparedStatement);
            }
            return flag;
        }

     public static boolean editContactFromDataBase(Contact contactToBeEdited, Contact contactUpgraded) {
         int updateCount;
         boolean flag = false;
         Connection connection = null;
         PreparedStatement preparedStatement = null;
         Statement statement;
         int contactId = 0;
         try {
             Class.forName(JDBC_DRIVER);
             connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             statement = connection.createStatement();
             String contactPhoneNumber = "\"" + contactToBeEdited.getPhoneNumber() + "\"";
             System.err.println(contactPhoneNumber);
             ResultSet resultSet = statement.executeQuery("SELECT * FROM contacts WHERE phone_number = "  + contactPhoneNumber);
             while (resultSet.next())
                 contactId = resultSet.getInt("int_key");
             if (contactId == 0) {
                 System.err.println("There is a problem during update process (related Contact could not be found with int_key value of Database table");
                 return false;
             }
             preparedStatement = connection.prepareStatement("UPDATE contacts SET phone_number = ?, name = ?, email_address = ?, country = ?, city = ?, street = ? WHERE int_key = ?");
             preparedStatement.setString(1, contactUpgraded.getPhoneNumber());
             preparedStatement.setString(2, contactUpgraded.getName());
             preparedStatement.setString(3, contactUpgraded.getEmailAddress());
             preparedStatement.setString(4, contactUpgraded.getCountry());
             preparedStatement.setString(5, contactUpgraded.getCity());
             preparedStatement.setString(6, contactUpgraded.getStreet());
             preparedStatement.setInt(7, contactId);
             updateCount = preparedStatement.executeUpdate();
             if (updateCount == 1) {
                 flag = true;
             }
         } catch (SQLException | ClassNotFoundException sqlEx) {
             System.err.println(Error.DATABASE_CONNECTION_OPEN_PROBLEM);
             sqlEx.printStackTrace();
         } finally {
             closeConnectionAndStatement(connection, preparedStatement);
         }
         return flag;
     }


    private static void closeConnectionAndStatement(Connection connection, Statement statement) {
        if(statement != null)
            try{
                statement.close();
            }catch(Exception e){
                System.err.println(Error.DATABASE_STATEMENT_CLOSE_PROBLEM);
                e.printStackTrace();
            }
        if(connection != null)
            try{
                connection.close();
            }catch(Exception e){
                System.err.println(Error.DATABASE_CONNECTION_CLOSE_PROBLEM);
                e.printStackTrace();
            }
    }
}
