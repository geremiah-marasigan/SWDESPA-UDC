/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import MODEL.CalendarDB;
import MODEL.LoginService;
import VIEW.LoginView;

/**
 *
 * @author ianona
 */
public class UltimateDesignChallenge {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        LoginView lv = new LoginView();
        LoginService ls = new LoginService(new CalendarDB());
        LoginController lc = new LoginController(ls, lv);
        lc.start();
    }
}
