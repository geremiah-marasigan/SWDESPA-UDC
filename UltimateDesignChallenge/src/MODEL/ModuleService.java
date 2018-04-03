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
    
    public Appointment toAppointment(ResultSet rs) throws SQLException {
	Appointment app = new Appointment(rs.getString(Appointment.COL_NAME),
                                          rs.getString(Appointment.COL_SDAY),
                                          rs.getString(Appointment.COL_EDAY),
                                          rs.getString(Appointment.COL_REPEAT),
                                          rs.getInt(Appointment.COL_STIME),
                                          rs.getInt(Appointment.COL_ETIME));
	return app;
    }
    
    public List<Appointment> getAllAppointments() {
	List <Appointment> apps = new ArrayList <> ();
	Connection connect = connection.getConnection();
	String query = 	"SELECT * " + " FROM " + Appointment.TABLE;

        try {
            PreparedStatement statement = connect.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
			
            while(rs.next()) {
		apps.add(toAppointment(rs));
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
}
