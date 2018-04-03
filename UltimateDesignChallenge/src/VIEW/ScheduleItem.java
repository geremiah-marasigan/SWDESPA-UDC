/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import CONTROLLER.ModuleController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ianona
 */
public class ScheduleItem extends JPanel{
    private ModuleController controller;
    
    private JLabel time;
    
    public ScheduleItem() {        
        time = new JLabel();
        
        this.add(time);
        
        setPreferredSize(new Dimension(260,45));
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
    }
    
    public ScheduleItem (ModuleController c, String time) {
        this();
        this.controller = c;
        this.setBackground(Color.white);

        this.time.setText(time);
    }
}
