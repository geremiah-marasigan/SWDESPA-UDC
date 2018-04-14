/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import MODEL.*;
import VIEW.*;
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
        
        view.setScheduleItems(model.getAllAppointments());
        view.setAgendaItems(model.getAllAppointments(), date);
    }
    
    public void addAppointment(Appointment app) {
        ((DoctorService)model).addAppointment(app);
        updateViews();
    }
    
    public void deleteAppointment(Appointment app) {
        ((DoctorService)model).deleteAppointment(app);
        updateViews();
    }
    
    public void deleteAppointment2(Appointment app) {
        ((DoctorService)model).deleteAppointment2(app);
        updateViews();
    }
    
    public void updateViews () {
        view.updateViews(model.getAllAppointments());
    }
    
    public void updateViews (User user, String date) {
        view.updateViews(model.getAllAppointments());
        ((DoctorView)view).setNotifItems(((DoctorController)this).getNotifications(user, date));
    }
    
    public List<Appointment> getAllSlots() {
        return model.getAllAppointments();
    }
    
    public List<Appointment> getDeletedSlots() {
        return ((DoctorService)model).getDeletedSlots();
    }
    
    public List<String> getDoctors() {
        return ((DoctorService)model).getDoctors();
    }
    
    public List<Notification> getNotifications(User user, String curDate) {
        return ((DoctorService)model).getNotifications(user, curDate);
    }
    
    public List<Appointment> getAppointments() {
        return ((DoctorService)model).getAppointments(getDoctors());
    }
    
    public void edit(String time) {
        ((DoctorView)view).edit(time);
    }
    
    public void delete (Appointment a) {
        ((DoctorService)model).addDeletedAppointment(a);
        updateViews();
    }
    
    public void deleteNotif (Notification n) {
        ((DoctorService)model).deleteNotification(n);
        updateViews(((DoctorView)view).getUser(), ((DoctorView)view).getDate());
    }
}
