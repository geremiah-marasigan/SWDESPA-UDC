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
        String query = "INSERT INTO " + Appointment.TABLE
                + " VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connect.prepareStatement(query);

            statement.setString(1, a.getName());
            statement.setString(2, a.getDate());
            statement.setInt(3, a.getTime());
            statement.setString(4, a.getTaken());

            statement.executeUpdate();
            statement.close();
            //connect.close();
            //System.out.println("[Appointment] INSERT SUCCESS!");
        } catch (SQLException ev) {
            ev.printStackTrace();
            //System.out.println("[Appointment] INSERT FAILED!");
        }
    }

    public void deleteAppointment(Appointment app) {
        Connection connect = connection.getConnection();
        String query = "DELETE FROM "
                + Appointment.TABLE
                + " WHERE " + Appointment.COL_NAME + " = ? AND "
                + Appointment.COL_DATE + " = ? AND "
                + Appointment.COL_TIME + " = ? AND "
                + Appointment.COL_TAKEN + " = ?";

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

    public void deleteDay(Appointment app) {
        Connection connect = connection.getConnection();
        String query = "DELETE FROM "
                + Appointment.TABLE
                + " WHERE " + Appointment.COL_NAME + " = ? AND "
                + Appointment.COL_DATE + " = ?";

        try {
            PreparedStatement statement = connect.prepareStatement(query);

            statement.setString(1, app.getName());
            statement.setString(2, app.getDate());
            statement.executeUpdate();

            statement.close();
            connect.close();
            System.out.println("[Appointment] DELETE SUCCESS!");
        } catch (SQLException ev) {
            System.out.println("[Appointment] DELETE FAILED!");
            ev.printStackTrace();
        }
    }

    private Notification toNotification(ResultSet rs) throws SQLException {
        Notification n = new Notification(rs.getString(Notification.COL_NAME),
                rs.getString(Notification.COL_DSENT),
                rs.getString(Notification.COL_MSG));

        return n;
    }

    public List<Notification> getNotifications(User user, String curDate) {
        List<Notification> notifs = new ArrayList<>();
        Connection connect = connection.getConnection();
        //String query = 	"SELECT * " + " FROM " + Notification.TABLE + " WHERE Name = ? AND DateSent LIKE ? ORDER BY DateSent";
        String query = "SELECT * " + " FROM " + Notification.TABLE + " WHERE Name = ? ORDER BY DateSent";
        try {
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setString(1, user.getLastname());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
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
        String query = "DELETE FROM "
                + Notification.TABLE
                + " WHERE " + Notification.COL_NAME + " = ? AND "
                + Notification.COL_DSENT + " = ? AND "
                + Notification.COL_MSG + " = ? ";

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
}
