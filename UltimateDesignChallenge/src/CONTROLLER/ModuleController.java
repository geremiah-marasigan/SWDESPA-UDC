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
public abstract class ModuleController {
    ModuleView view;
    ModuleService model;
    
    public ModuleController(ModuleService model, ModuleView view) {
        this.model = model;
        this.view = view;
    }
    
    public abstract void start();
    
}
