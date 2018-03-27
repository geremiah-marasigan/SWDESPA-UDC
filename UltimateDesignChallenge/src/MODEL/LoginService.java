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
public class LoginService {
    private CalendarDB connection;
	
    public LoginService (CalendarDB cdb) {
	this.connection = cdb;
    }
	
    private User toUser(ResultSet rs) throws SQLException {
	User user = new User(rs.getString(User.COL_EMAIL),
                             rs.getString(User.COL_PASSWORD),
                             rs.getString(User.COL_TYPE),
                             rs.getString(User.COL_FIRSTNAME),
                             rs.getString(User.COL_LASTNAME));
        
	return user;
    }

    public List<User> getAll() {
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
	
            System.out.println("[User] SELECT SUCCESS!");
	} catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[user] SELECT FAILED!");
            return null;
	}	
		
        return users;
    }
}
