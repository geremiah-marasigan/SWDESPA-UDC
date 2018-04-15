/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODEL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ianona
 */

public abstract class ModuleService {
    protected CalendarDB connection;
    
    public ModuleService(CalendarDB connection) {
        this.connection = connection;
    }
    
    private User toUser(ResultSet rs) throws SQLException {
	User user = new User(rs.getString(User.COL_EMAIL),
                             rs.getString(User.COL_PASSWORD),
                             rs.getString(User.COL_TYPE),
                             rs.getString(User.COL_FIRSTNAME),
                             rs.getString(User.COL_LASTNAME));
        
	return user;
    }
    
    public List<User> getAllUsers() {
	List <User> users = new ArrayList <> ();
	Connection connect = connection.getConnection();
	String query = 	"SELECT * " + " FROM " + User.TABLE;

        try {
            PreparedStatement statement = connect.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
			
            while(rs.next()) {
		users.add(toUser(rs));
            }
			
            rs.close();
            statement.close();
            connect.close();
	
        //    System.out.println("[Client] SELECT SUCCESS!");
	} catch (SQLException e) {
            e.printStackTrace();
        //    System.out.println("[Client] SELECT FAILED!");
            return null;
	}	
		
        return users;
    }
    
    public Appointment toAppointment(ResultSet rs) throws SQLException {
	Appointment app = new Appointment(rs.getString(Appointment.COL_NAME),
                                          rs.getString(Appointment.COL_DATE),
                                          rs.getInt(Appointment.COL_TIME),
                                          rs.getString(Appointment.COL_TAKEN));
	return app;
    }
    
    public List<Appointment> getAllAppointments() {
	List <Appointment> apps = new ArrayList <> ();
	Connection connect = connection.getConnection();
	String query = 	"SELECT * " + " FROM " + Appointment.TABLE  +" ORDER BY " + Appointment.COL_DATE +", "+Appointment.COL_TIME;

        try {
            PreparedStatement statement = connect.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
			
            while(rs.next()) {
		apps.add(toAppointment(rs));
            }
			
            rs.close();
            statement.close();
            connect.close();
	
        //    System.out.println("[Appointment] SELECT SUCCESS!");
	} catch (SQLException e) {
            e.printStackTrace();
        //    System.out.println("[Appointment] SELECT FAILED!");
            return null;
	}	
		
        return apps;
    }
    
    public List<Appointment> getSlots(String date) {
	List <Appointment> apps = new ArrayList <> ();
	Connection connect = connection.getConnection();
	String query = 	"SELECT * " + " FROM " + Appointment.TABLE  + " WHERE " + Appointment.COL_DATE + " = ?";

        try {
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setString(1, date);
            ResultSet rs = statement.executeQuery();
			
            while(rs.next()) {
		apps.add(toAppointment(rs));
            }
			
            rs.close();
            statement.close();
            connect.close();
	
        //    System.out.println("[Appointment] SELECT SUCCESS!");
	} catch (SQLException e) {
            e.printStackTrace();
        //    System.out.println("[Appointment] SELECT FAILED!");
            return null;
	}	
		
        return apps;
    }
}
