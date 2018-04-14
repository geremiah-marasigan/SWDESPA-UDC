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
    private String name, startDay, endDay, repeat;
    private int startTime, endTime;
    
    // FOR THE DATABASE
    public static final String TABLE = "Appointment";
    public static final String TABLE2 = "DeletedSlots";
    public static final String COL_NAME = "Name";
    public static final String COL_SDAY = "StartDay";
    public static final String COL_EDAY = "EndDay";
    public static final String COL_REPEAT = "Repeat";
    public static final String COL_STIME = "StartTime";
    public static final String COL_ETIME = "EndTime";
    
    public Appointment(String name, String startDay, String endDay, String repeat, int startTime, int endTime) {
        this.name = name;
        this.startDay = startDay;
        this.endDay = endDay;
        this.repeat = repeat;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
    
    
}
