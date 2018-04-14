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
public class ClientService extends ModuleService {
    public ClientService(CalendarDB connection) {
        super(connection);
    }
    
    public void addAppointment(Appointment a) {
        Connection connect = connection.getConnection();
        String query = 	"INSERT INTO " + Appointment.TABLE +
			" VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connect.prepareStatement(query);
			
            statement.setString(1, a.getName());
            statement.setString(2, a.getStartDay());
            statement.setString(3, a.getEndDay());
            statement.setString(4, a.getRepeat());
            statement.setInt(5, a.getStartTime());
            statement.setInt(6, a.getEndTime());
			
            statement.executeUpdate();
            statement.close();
            connect.close();
            System.out.println("[Appointment] INSERT SUCCESS!");
        } catch (SQLException ev) {
            ev.printStackTrace();
            System.out.println("[Appointment] INSERT FAILED!");
        }	
    }	
    
    public void addDelete(Appointment a, String date) {
        Connection connect = connection.getConnection();
        String query = 	"INSERT INTO deletedslots" +
			" VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connect.prepareStatement(query);
			
            statement.setString(1, a.getName());
            statement.setString(2, date);
            statement.setString(3, date);
            statement.setInt(4, a.getStartTime());
            statement.setInt(5, a.getEndTime());
			
            statement.executeUpdate();
            statement.close();
            connect.close();
            System.out.println("[Appointment] INSERT SUCCESS!");
        } catch (SQLException ev) {
            ev.printStackTrace();
            System.out.println("[Appointment] INSERT FAILED!");
        }	
    }
    
    public Appointment toDelete(ResultSet rs) throws SQLException {
	Appointment app = new Appointment(rs.getString(Appointment.COL_NAME),
                                          rs.getString(Appointment.COL_SDAY),
                                          rs.getString(Appointment.COL_EDAY),
                                          "None",
                                          rs.getInt(Appointment.COL_STIME),
                                          rs.getInt(Appointment.COL_ETIME));
	return app;
    }
    
    public List<Appointment> getAllDeleted() {
        List <Appointment> apps = new ArrayList <> ();
	Connection connect = connection.getConnection();
	String query = 	"SELECT * " + " FROM deletedslots" + " ORDER BY " + Appointment.COL_STIME;

        try {
            PreparedStatement statement = connect.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
			
            while(rs.next()) {
		apps.add(toDelete(rs));
            }
			
            rs.close();
            statement.close();
            connect.close();
	
            System.out.println("[Appointment] SELECT SUCCESS!");
	} catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[Appointment] SELECT FAILED!");
            return null;
	}	
		
        return apps;	
    }
    
    public void deleteAppointment(Appointment app) {
	Connection connect = connection.getConnection();
	String query = 	"DELETE FROM " + 
			Appointment.TABLE +
			" WHERE " + Appointment.COL_NAME + " = ? AND " +
                                    Appointment.COL_STIME + " = ? AND " +
                                    Appointment.COL_ETIME + " = ? ";
		
	try {
            PreparedStatement statement = connect.prepareStatement(query);
			
            statement.setString(1, app.getName());
            statement.setInt(2, app.getStartTime());
            statement.setInt(3, app.getEndTime());
            statement.executeUpdate();
			
            statement.close();
            connect.close();
            System.out.println("[Appointment] DELETE SUCCESS!");
        } catch (SQLException ev) {
            System.out.println("[Appointment] DELETE FAILED!");
            ev.printStackTrace();
	}	
    }
    
    private User toUser(ResultSet rs) throws SQLException {
	User user = new User(rs.getString(User.COL_EMAIL),
                             rs.getString(User.COL_PASSWORD),
                             rs.getString(User.COL_TYPE),
                             rs.getString(User.COL_FIRSTNAME),
                             rs.getString(User.COL_LASTNAME));
        
	return user;
    }
    
    public List<User> getFilterUsers(String name) {
	List <User> users = new ArrayList <> ();
	Connection connect = connection.getConnection();
	String query = 	"SELECT * " + " FROM " + User.TABLE + " WHERE " + User.COL_LASTNAME + " = ? OR " + User.COL_TYPE + " = CLIENT";

        try {
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setString(1,name);
            
            ResultSet rs = statement.executeQuery();
			
            while(rs.next()) {
		users.add(toUser(rs));
            }
			
            rs.close();
            statement.close();
            connect.close();
	
            System.out.println("[Appointment] SELECT SUCCESS!");
	} catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[Appointment] SELECT FAILED!");
            return null;
	}	
		
        return users;
    }
}
