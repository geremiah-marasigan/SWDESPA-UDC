/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import MODEL.LoginService;
import MODEL.ModuleService;
import MODEL.User;
import VIEW.LoginView;
import VIEW.ModuleFactory;
import VIEW.ModuleView;
import java.util.List;

/**
 *
 * @author ianona
 */
public class LoginController {
    private LoginService model;
    private LoginView view;
    private ModuleFactory factory;
	
    public LoginController (LoginService model, LoginView view) {
        this.model = model;
        this.view = view;
        factory = new ModuleFactory();
    }

    public void start() {
	view.attach(this);
    }
    
    public List<User> getAll () {
        return model.getAll();
    }    
    
    public void constructModule (User user) {   
        ModuleView mv = factory.getView(user.getType());
        ModuleService ms = factory.getModel(user.getType());
        ModuleController mc = factory.getController(user.getType(), ms, mv);
        mv.setUser(user);
        mc.start();
    }
}
