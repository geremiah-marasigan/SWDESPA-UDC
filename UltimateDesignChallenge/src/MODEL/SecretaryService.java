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
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author ianona
 */
public class SecretaryService extends ModuleService{
    public SecretaryService(CalendarDB connection) {
        super(connection);
    }
    
    public void addNotification(User a, String msg){
        Connection connect = connection.getConnection();
        String query = 	"INSERT INTO " + Notification.TABLE +
			" VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connect.prepareStatement(query);
			
            statement.setString(1, a.getLastname());
            Calendar today;
            today = Calendar.getInstance();
            System.out.println(today.toString());
            String todayString;
            todayString = "" + (today.get(Calendar.MONTH)+1) + "/" + today.get(Calendar.DAY_OF_MONTH) + "/" + today.get(Calendar.YEAR) + " " + today.get(Calendar.HOUR_OF_DAY) + ":" + today.get(Calendar.MINUTE);
            statement.setString(2, todayString);  
            statement.setString(3, msg);
            statement.executeUpdate();
            statement.close();
            connect.close();
            System.out.println("[Notification] INSERT SUCCESS!");
        } catch (SQLException ev) {
            ev.printStackTrace();
            System.out.println("[Notification] INSERT FAILED!");
        }
    }
    
    public void addAppointment(Appointment a) {
        Connection connect = connection.getConnection();
        String query = "UPDATE " + Appointment.TABLE
                + " SET " + Appointment.COL_TAKEN + " = ? WHERE " + Appointment.COL_TIME + " = ? AND " + Appointment.COL_NAME + " = ? AND " + Appointment.COL_DATE + " = ?";
        try {
            PreparedStatement statement = connect.prepareStatement(query);

            statement.setString(1, a.getTaken());
            statement.setInt(2, a.getTime());
            statement.setString(3, a.getName());
            statement.setString(4, a.getDate());

            statement.executeUpdate();
            statement.close();
            connect.close();
            System.out.println("[Appointment] UPDATE SUCCESS!");
        } catch (SQLException ev) {
            ev.printStackTrace();
            System.out.println("[Appointment] UPDATE FAILED!");
        }
    }	
    
    public void deleteAppointment(Appointment app) {
	Connection connect = connection.getConnection();
	String query = 	"DELETE FROM " + 
			Appointment.TABLE +
			" WHERE " + Appointment.COL_NAME + " = ? AND " +
                                    Appointment.COL_DATE + " = ? AND " +
                                    Appointment.COL_TIME + " = ? AND " +
                                    Appointment.COL_TAKEN + " = ?";
		
	try {
            PreparedStatement statement = connect.prepareStatement(query);
			
            statement.setString(1, app.getName());
            statement.setString(2, app.getDate());
            statement.setInt(3, app.getTime());
            statement.setString(4, app.getTaken());
            statement.executeUpdate();
			
            statement.close();
            connect.close();
            System.out.println("[Appointment] DELETE SUCCESS!");
        } catch (SQLException ev) {
            System.out.println("[Appointment] DELETE FAILED!");
            ev.printStackTrace();
	}	
    }
    
    public List<Appointment> getAllFreeAppointments(String date) {
        List<Appointment> apps = new ArrayList<>();
        Connection connect = connection.getConnection();
        String query = "SELECT * " + " FROM " + Appointment.TABLE + " WHERE " + Appointment.COL_DATE + " =? AND " + Appointment.COL_TAKEN + " = 'NOT_TAKEN'";

        try {
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setString(1, date);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
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
    
    public List<Appointment> getSlot(String date, String time) {
	List <Appointment> apps = new ArrayList <> ();
	Connection connect = connection.getConnection();
	String query = 	"SELECT * " + " FROM " + Appointment.TABLE  + " WHERE " + Appointment.COL_DATE + " = ? AND " + Appointment.COL_TIME + " =?";

        try {
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setString(1, date);
            statement.setString(2, time);
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