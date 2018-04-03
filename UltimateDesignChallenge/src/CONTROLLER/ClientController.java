/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import MODEL.ModuleService;
import VIEW.ModuleView;

/**
 *
 * @author ianona
 */
public class ClientController extends ModuleController {
    
    public ClientController(ModuleService model, ModuleView view) {
        super(model, view);
    }
    
    public void start () {
        view.setController(this);
        // view.setScheduleItems();
    }
    
    @Override
    public String toString () {
        return "Succesfully attached ClientController";
    }
}
