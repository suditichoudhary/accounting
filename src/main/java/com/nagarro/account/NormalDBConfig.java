package com.nagarro.account;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class NormalDBConfig {
	public static void getConnectionWithAccess() {

		// variables
	    Connection connection = null;
	    Statement statement = null;
	    ResultSet resultSet = null;

	    // Step 1: Loading driver
	    try {
	        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	    } catch (ClassNotFoundException cnfex) {
	        System.out.println("Problem in loading or "
	                + "registering MS Access JDBC driver");
	        cnfex.printStackTrace();
	    }

	    // Step 2: open db  connection
	    try {

	        String msAccDB = "/Users/suditi/Desktop/accountsdb.accdb";


	        String dbURL = "jdbc:ucanaccess://" + msAccDB;

	        // Step 2.A: Create and get connection using DriverManager class
	        connection = DriverManager.getConnection(dbURL);

	        // Step 2.B: Creating JDBC Statement
	        statement = connection.createStatement();

	        // Step 2.C: Executing SQL & retrieve data into ResultSet
	        resultSet = statement.executeQuery("SELECT * FROM account");

	        System.out.println("ID\tName\t\t\tAge\tsalary");
	         while (resultSet.next()) {
	            System.out.println(resultSet.getInt(1) + "\t" +
	                    resultSet.getString(2) + "\t" +
	                    resultSet.getString(3));
	        }
	    } catch (SQLException sqlex) {
	        sqlex.printStackTrace();
	    } finally {
	        try {
	            if (null != connection) {

	                // cleanup resources, once after processing
	                resultSet.close();
	                statement.close();

	                // and then finally close connection
	                connection.close();

	


	    }
    } catch (SQLException sqlex) {
        sqlex.printStackTrace();
    }
}
}
}
