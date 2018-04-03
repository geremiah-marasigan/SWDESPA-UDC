/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import MODEL.CalendarDB;
import MODEL.ClientService;
import MODEL.DoctorService;
import MODEL.LoginService;
import MODEL.ModuleService;
import MODEL.SecretaryService;
import MODEL.User;
import VIEW.Builder;
import VIEW.ClientBuilder;
import VIEW.DoctorBuilder;
import VIEW.LoginView;
import VIEW.SecretaryBuilder;
import java.util.List;

/**
 *
 * @author ianona
 */
public class LoginController {
    private LoginService model;
    private LoginView view;
    private Builder builder;
	
    public LoginController (LoginService model, LoginView view) {
        this.model = model;
        this.view = view;
    }

    public void start() {
	view.attach(this);
    }
    
    public List<User> getAll () {
        return model.getAll();
    }    
    
    public void setBuilder (Builder b) {
        this.builder = b;
    }
    
    public void constructModule (User user) {
        builder.buildModule();
        builder.buildIcon();
        builder.buildCustom(user);
        
        ModuleController mc = null;
        ModuleService ms = null;
        
        if (builder instanceof DoctorBuilder) {
            ms = new DoctorService(new CalendarDB());
            mc = new DoctorController(ms, builder.getView());
        }
            
        else if (builder instanceof SecretaryBuilder) {
            ms = new SecretaryService(new CalendarDB());
            mc = new SecretaryController(ms, builder.getView());
        }
            
        else if (builder instanceof ClientBuilder) {
            ms = new ClientService(new CalendarDB());
            mc = new ClientController(ms, builder.getView());
        }
            
        mc.start();
    }
}
