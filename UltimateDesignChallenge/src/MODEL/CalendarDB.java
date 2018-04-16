/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODEL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ianona
 */
public class CalendarDB {
    private final static String DRIVER_NAME = "com.mysql.jdbc.Driver";
    //private final static String URL = "jdbc:mysql://localhost:3306/";
    private final static String URL = "jdbc:mysql://192.168.0.101:3306/";
    private final static String USERNAME = "udc";
    private final static String PASSWORD = "udc";
    private final static String DATABASE = "UDC";
	
    public Connection getConnection () {
        try {
            Class.forName(DRIVER_NAME);
            Connection connection = DriverManager.getConnection(
                                    URL + 
                                    DATABASE + "?autoReconnect=true&useSSL=false", 
                                    USERNAME, 
                                    PASSWORD);
			
        //    System.out.println("[MYSQL] Connection successful!");
            return connection;
	} catch (SQLException e) {
            System.out.println("[MYSQL] SQL error, connection unsuccesful!");
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("[MYSQL] Class not found, connection unsuccesful!");
            e.printStackTrace();
            return null;
	}
    }
}
