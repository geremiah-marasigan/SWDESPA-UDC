/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODEL;

/**
 *
 * @author zachmarasigan
 */
public class Notification {
    private String name, date, message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    // FOR THE DATABASE
    public static final String TABLE = "Notification";
    public static final String COL_NAME = "Name";
    public static final String COL_DSENT = "DateSent";
    public static final String COL_MSG = "Message";
    
    public Notification(String name, String date, String message) {
        this.name = name;
        this.date = date;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setStartDay(String date) {
        this.date = date;
    }
}
