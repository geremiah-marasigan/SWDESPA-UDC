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
import java.util.Calendar;

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
            todayString = "" + today.get(Calendar.YEAR) + "/" + today.get(Calendar.MONTH) + "/" + today.get(Calendar.DAY_OF_MONTH) + " " + today.get(Calendar.HOUR_OF_DAY) + ":" + today.get(Calendar.MINUTE);
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
    
    private User toUser(ResultSet rs) throws SQLException {
	User user = new User(rs.getString(User.COL_EMAIL),
                             rs.getString(User.COL_PASSWORD),
                             rs.getString(User.COL_TYPE),
                             rs.getString(User.COL_FIRSTNAME),
                             rs.getString(User.COL_LASTNAME));
        
	return user;
    }
    
    public String getTypeOfUserGivenAppointment(String name){
        User user;
	Connection connect = connection.getConnection();
	String query = 	"SELECT * " + " FROM " + User.TABLE + " WHERE " + User.COL_FIRSTNAME + " = ?";

        try {
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setString(1,name);
            
            ResultSet rs = statement.executeQuery();
            rs.next();
            user = toUser(rs);
			
            rs.close();
            statement.close();
            connect.close();
	
            System.out.println("[Type] SELECT SUCCESS!");
	} catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[Type] SELECT FAILED!");
            return "";
	}	
		
        return user.getType();
    }
}
