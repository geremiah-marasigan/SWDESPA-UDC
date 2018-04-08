/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODEL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

/**
 *
 * @author ianona
 */
public class SecretaryService extends ModuleService{
    public SecretaryService(CalendarDB connection) {
        super(connection);
    }
    
    public void addNotification(User a){
        Connection connect = connection.getConnection();
        String query = 	"INSERT INTO " + Notification.TABLE +
			" VALUES (?, ?)";
        try {
            PreparedStatement statement = connect.prepareStatement(query);
			
            statement.setString(1, a.getLastname());
            Calendar today;
            today = Calendar.getInstance();
            System.out.println(today.toString());
            String todayString;
            todayString = "" + today.get(Calendar.YEAR) + "/" + today.get(Calendar.MONTH) + "/" + today.get(Calendar.DAY_OF_MONTH) + " " + today.get(Calendar.HOUR_OF_DAY) + ":" + today.get(Calendar.MINUTE);
            statement.setString(2, todayString);        
            statement.executeUpdate();
            statement.close();
            connect.close();
            System.out.println("[Notification] INSERT SUCCESS!");
        } catch (SQLException ev) {
            ev.printStackTrace();
            System.out.println("[Notification] INSERT FAILED!");
        }
    }
}
