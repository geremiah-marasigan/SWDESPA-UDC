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
public class SecretaryController extends ModuleController{
    
    public SecretaryController(ModuleService model, ModuleView view) {
        super(model, view);
    }
    
    public void start () {
        view.setController(this);
    }
}
