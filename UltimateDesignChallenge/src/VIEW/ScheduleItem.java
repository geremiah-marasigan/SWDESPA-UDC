/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import CONTROLLER.ModuleController;
import MODEL.Appointment;
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
public class ScheduleItem extends JPanel {

    private ModuleController controller;

    private JLabel time;
    private JLabel name;

    public ScheduleItem() {
        time = new JLabel();
        name = new JLabel();

        this.add(time);
        this.add(name);

        setPreferredSize(new Dimension(260, 45));
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
    }

    public ScheduleItem(ModuleController c, String time, boolean first, Appointment app, String type) {
        this();
        System.out.println(type);
        this.controller = c;
        if (first) {
            System.out.println("First");
            
            if (type.equals("doctor")) {
                System.out.println("ENTERED DOCTOR");
                this.name.setText("Dr. " + app.getName());
            }
            else if (type.equals("client")){
                System.out.println("ENTERED TAKEN");
                this.name.setText("Taken");
            }
        } else {
            this.name.setText("");
        }

        if (type.equals("doctor")) {
            this.setBackground(new Color(186, 255, 133));
        } else if (type.equals("client")) {
            this.setBackground(Color.RED);
        } else {
            this.setBackground(Color.white);
        }

        this.time.setText(time);
    }

    public ScheduleItem(ModuleController c, String time) {
        this();
        this.controller = c;
        this.setBackground(Color.white);

        this.time.setText(time);
        this.name.setText("");
    }
}
