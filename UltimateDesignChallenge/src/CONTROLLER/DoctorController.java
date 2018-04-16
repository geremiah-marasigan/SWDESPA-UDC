/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import MODEL.*;
import VIEW.*;
import java.sql.Connection;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author ianona
 */
public class DoctorController extends ModuleController {
    public DoctorController(ModuleService model, ModuleView view) {
        super(model, view);
    }
    
    public void start () {
        view.setController(this);
        
        GregorianCalendar cal = new GregorianCalendar();
        int dayBound = cal.get(GregorianCalendar.DAY_OF_MONTH);
        int monthBound = cal.get(GregorianCalendar.MONTH) + 1;
        int yearBound = cal.get(GregorianCalendar.YEAR);
        String date = monthBound + "/" + dayBound + "/" + yearBound;
        
        updateViews(date, ((DoctorView)view).getUser());
    }
    
    public void addAppointment(Appointment app) {
        ((DoctorService)model).addAppointment(app);
        updateViews(app.getDate(), ((DoctorView)view).getUser());
    }
    
    public void deleteAppointment(Appointment app) {
        ((DoctorService)model).deleteAppointment(app);
        updateViews(app.getDate(), ((DoctorView)view).getUser());
    }
    
    public void deleteDay(Appointment app) {
        ((DoctorService)model).deleteDay(app);
        updateViews(app.getDate(), ((DoctorView)view).getUser());
    }
    
    public void updateViews (String date, User user) {
        view.updateViews(model.getSlots(date,user));
        ((DoctorView)view).setNotifItems(((DoctorController)this).getNotifications(user, date));
    }
    
    public List<Appointment> getSlots(String date, User user) {
        return model.getSlots(date, user);
    }
    
    public User getUser() {
        return ((DoctorView)view).getUser();
    }
    
    public List<Appointment> getAllSlots() {
        return model.getAllAppointments();
    }
    
    public List<Appointment> getSlots(String date) {
        return model.getSlots(date);
    }
    
    public List<Notification> getNotifications(User user, String curDate) {
        return ((DoctorService)model).getNotifications(user, curDate);
    }
    
    public void edit(String time) {
        ((DoctorView)view).edit(time);
    }
    
    public void deleteNotif (Notification n) {
        ((DoctorService)model).deleteNotification(n);
        updateViews(((DoctorView)view).getDate(), ((DoctorView)view).getUser());
    }
    
}
