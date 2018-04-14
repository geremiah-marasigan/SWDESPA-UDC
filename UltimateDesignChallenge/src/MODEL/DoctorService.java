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
public class DoctorService extends ModuleService {

    public DoctorService(CalendarDB connection) {
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
        //    System.out.println("[Appointment] INSERT SUCCESS!");
        } catch (SQLException ev) {
            ev.printStackTrace();
         //   System.out.println("[Appointment] INSERT FAILED!");
        }	
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
        //    System.out.println("[Appointment] DELETE SUCCESS!");
        } catch (SQLException ev) {
        //    System.out.println("[Appointment] DELETE FAILED!");
            ev.printStackTrace();
	}	
    }
    
    public void deleteAppointment2(Appointment app) {
	Connection connect = connection.getConnection();
	String query = 	"DELETE FROM " + 
			Appointment.TABLE2 +
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
        //    System.out.println("[DeletedSlots] DELETE SUCCESS!");
        } catch (SQLException ev) {
        //    System.out.println("[DeletedSlots] DELETE FAILED!");
            ev.printStackTrace();
	}	
    }
    
    public Appointment toDeletedAppointment(ResultSet rs) throws SQLException {
	Appointment app = new Appointment(rs.getString(Appointment.COL_NAME),
                                          rs.getString(Appointment.COL_SDAY),
                                          rs.getString(Appointment.COL_EDAY),
                                          null,
                                          rs.getInt(Appointment.COL_STIME),
                                          rs.getInt(Appointment.COL_ETIME));
	return app;
    }
    
    public List<Appointment> getDeletedSlots() {
	List <Appointment> apps = new ArrayList <> ();
	Connection connect = connection.getConnection();
	String query = 	"SELECT * " + " FROM " + Appointment.TABLE2;

        try {
            PreparedStatement statement = connect.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
			
            while(rs.next()) {
		apps.add(toDeletedAppointment(rs));
            }
			
            rs.close();
            statement.close();
            connect.close();
	
        //    System.out.println("[DeletedSlots] SELECT SUCCESS!");
	} catch (SQLException e) {
            e.printStackTrace();
        //    System.out.println("[DeletedSlots] SELECT FAILED!");
            return null;
	}	
		
        return apps;
    }
    
    public List<Appointment> getAppointments(List<String> doctors) {
	List <Appointment> apps = new ArrayList <> ();
	Connection connect = connection.getConnection();
	String query = 	"SELECT * " + " FROM " + Appointment.TABLE;

        try {
            PreparedStatement statement = connect.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
			
            while(rs.next()) {
                boolean good = true;
                for (String doc:doctors) {
                    if (rs.getString(Appointment.COL_NAME).equalsIgnoreCase(doc))
                        good = false;
                }
                if (good)
                    apps.add(toAppointment(rs));
            }
			
            rs.close();
            statement.close();
            connect.close();
	
        //    System.out.println("[Appointments] SELECT SUCCESS!");
	} catch (SQLException e) {
            e.printStackTrace();
        //    System.out.println("[Appointments] SELECT FAILED!");
            return null;
	}	
		
        return apps;
    }
    
    public List<String> getDoctors() {
	List <String> docs = new ArrayList <> ();
	Connection connect = connection.getConnection();
	String query = 	"SELECT * " + " FROM " + User.TABLE + " WHERE Type = \"DOCTOR\"";

        try {
            PreparedStatement statement = connect.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
			
            while(rs.next()) {
		docs.add(rs.getString(User.COL_LASTNAME));
            }
			
            rs.close();
            statement.close();
            connect.close();
	
        //    System.out.println("[GetDoctors] SELECT SUCCESS!");
	} catch (SQLException e) {
            e.printStackTrace();
        //    System.out.println("[GetDoctors] SELECT FAILED!");
            return null;
	}	
		
        return docs;
    }
    
    private Notification toNotification(ResultSet rs) throws SQLException {
	Notification n = new Notification(rs.getString(Notification.COL_NAME),
                             rs.getString(Notification.COL_DSENT),
                             rs.getString(Notification.COL_MSG));
        
	return n;
    }
    
    public List<Notification> getNotifications(User user, String curDate) {
	List <Notification> notifs = new ArrayList <> ();
	Connection connect = connection.getConnection();
	String query = 	"SELECT * " + " FROM " + Notification.TABLE + " WHERE Name = ? AND DateSent LIKE ? ORDER BY DateSent";

        try {
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setString(1, user.getLastname());
            statement.setString(2, "%"+curDate+"%");
            ResultSet rs = statement.executeQuery();
			
            while(rs.next()) {
		notifs.add(toNotification(rs));
            }
			
            rs.close();
            statement.close();
            connect.close();
	
        //    System.out.println("[GetDoctors] SELECT SUCCESS!");
	} catch (SQLException e) {
            e.printStackTrace();
        //    System.out.println("[GetDoctors] SELECT FAILED!");
            return null;
	}	
		
        return notifs;
    }
    
    public void deleteNotification(Notification notif) {
	Connection connect = connection.getConnection();
	String query = 	"DELETE FROM " + 
			Notification.TABLE +
			" WHERE " + Notification.COL_NAME + " = ? AND " +
                                    Notification.COL_DSENT + " = ? AND " +
                                    Notification.COL_MSG + " = ? ";
		
	try {
            PreparedStatement statement = connect.prepareStatement(query);
			
            statement.setString(1, notif.getName());
            statement.setString(2, notif.getDate());
            statement.setString(3, notif.getMessage());
            statement.executeUpdate();
			
            statement.close();
            connect.close();
        //    System.out.println("[Notification] DELETE SUCCESS!");
        } catch (SQLException ev) {
        //    System.out.println("[Notification] DELETE FAILED!");
            ev.printStackTrace();
	}	
    }
    
    public void addDeletedAppointment(Appointment a) {
        Connection connect = connection.getConnection();
        String query = 	"INSERT INTO " + Appointment.TABLE2 +
			" VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connect.prepareStatement(query);
			
            statement.setString(1, a.getName());
            statement.setString(2, a.getStartDay());
            statement.setString(3, a.getEndDay());
            statement.setInt(4, a.getStartTime());
            statement.setInt(5, a.getEndTime());
			
            statement.executeUpdate();
            statement.close();
            connect.close();
        //    System.out.println("[DeletedSlots] INSERT SUCCESS!");
        } catch (SQLException ev) {
            ev.printStackTrace();
        //    System.out.println("[DeletedSlots] INSERT FAILED!");
        }	
    }	
}
