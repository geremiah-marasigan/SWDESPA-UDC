/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODEL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            System.out.println("[Appointment] INSERT SUCCESS!");
        } catch (SQLException ev) {
            ev.printStackTrace();
            System.out.println("[Appointment] INSERT FAILED!");
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
            System.out.println("[Appointment] DELETE SUCCESS!");
        } catch (SQLException ev) {
            System.out.println("[Appointment] DELETE FAILED!");
            ev.printStackTrace();
	}	
    }
}
