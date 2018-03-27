/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author ianona
 */
public abstract class Builder {
    protected ModuleView module;
    protected Container mainPane;
    protected JLabel icon;
    
    // GENERIC MODULE COMPONENTS
    public void buildModule () {
        module = new ModuleView();
        mainPane = module.getContentPane();
    }
    
    // SPECIFY CUSTOM MODULE COMPONENTS IN THEIR CONCRETE CLASSES
    public abstract void buildIcon();
    
    public abstract void buildCustom();
}