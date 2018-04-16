/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import MODEL.Appointment;
import MODEL.ModuleService;
import MODEL.SecretaryService;
import MODEL.User;
import VIEW.ModuleView;
import VIEW.SecretaryView;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ianona
 */
public class SecretaryController extends ModuleController{
    
    public SecretaryController(ModuleService model, ModuleView view) {
        super(model, view);
    }
    
    public void start () {
        view.setController(this);
    }
    
    public List<User> getDoctors(){
        List<User> users = ((SecretaryService)model).getAllUsers();
        List<User> doctors = new ArrayList<>();
        for(User user: users){
            if("DOCTOR".equals(user.getType())){
                doctors.add(user);
            }
        }
        return doctors;
    }
    
    public void updateViews () {
        view.updateViews(model.getAllAppointments());
    }
    
    public void updateViews(String date){
        view.updateViews(model.getSlots(date));
    }
   
    
    public List<Appointment> getAllAppointments(){
        return model.getAllAppointments();
    }
    
    public List<User> getAllUsers(){
        return model.getAllUsers();
    }
    
    public void notifyDoctor(User d, String msg){
        ((SecretaryService)model).addNotification(d, msg);
    }
    
    public List<Appointment> getSlots(String date) {
        return model.getSlots(date);
    }
    
    public void deleteAppointment(Appointment app) {
        ((SecretaryService)model).deleteAppointment(app);
        updateViews(app.getDate());
    }
    
    public void filterViews(String name) {
        ((SecretaryView)view).filterViews(model.getAllAppointments(), name);
    }
    
    public List<Appointment> getAllFree(String date){
        return ((SecretaryService)model).getAllFreeAppointments(date);
    }
    
    public void addAppointment(Appointment app) {
        ((SecretaryService)model).addAppointment(app);
        updateViews(app.getDate());
    }
    
    public List<Appointment> getSlot(String date, int time){
        return ((SecretaryService)model).getSlot(date, String.valueOf(time));
    }
}
