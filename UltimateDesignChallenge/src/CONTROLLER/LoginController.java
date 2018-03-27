/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import MODEL.LoginService;
import MODEL.User;
import VIEW.Builder;
import VIEW.LoginView;
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
        builder.buildCustom();
    }
}
