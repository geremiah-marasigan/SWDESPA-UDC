/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODEL;

/**
 *
 * @author ianona
 */
public class Appointment {
    private String name, date, taken;
    private int time;
    
    // FOR THE DATABASE
    public static final String TABLE = "Appointment";
    public static final String COL_NAME = "Name";
    public static final String COL_DATE = "Date";
    public static final String COL_TIME = "Time";
    public static final String COL_TAKEN = "Taken";
    
    public Appointment(String name, String date, int time, String taken) {
        this.name = name;
        this.time = time;
        this.date = date;
        this.taken = taken;
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

    public void setDate(String date) {
        this.date = date;
    }

    public String getTaken() {
        return taken;
    }

    public void setTaken(String taken) {
        this.taken = taken;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    
    
}
