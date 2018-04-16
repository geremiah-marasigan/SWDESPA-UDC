/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import CONTROLLER.*;
import MODEL.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ianona
 */
public class ScheduleItem extends JPanel {

    private ModuleController controller;

    //DOCTOR
    private JButton btnDelete, btnEdit;
    private String date, doctor;
    private Appointment app;

    private JLabel time;
    private JLabel name;

    public ScheduleItem() {
        time = new JLabel();
        name = new JLabel();

        btnDelete = new JButton();
        btnDelete.addActionListener(new btnDelete_Action());
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("RESOURCES/btnDelete.png")));
            btnDelete.setIcon(new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        } catch (IOException e) {
            System.out.println("FILE NOT FOUND");
        }
        btnEdit = new JButton();
        btnEdit.addActionListener(new btnEdit_Action());
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("RESOURCES/btnEdit.png")));
            btnEdit.setIcon(new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        } catch (IOException e) {
            System.out.println("FILE NOT FOUND");
        }

        this.add(time);
        this.add(name);

        setPreferredSize(new Dimension(260, 45));
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
    }

    // CLIENT
    public ScheduleItem(ModuleController c, String time, List<Appointment> apps, User user) {
        this();
        this.controller = c;
        this.setBackground(Color.white);
        if (apps.size() == 1) {
            this.app = apps.get(0);
            if (apps.get(0).getTaken().equals("NOT_TAKEN")) {
                this.setBackground(new Color(186, 255, 133));
                this.name.setText("Dr. " + apps.get(0).getName());
            } else {
                this.setBackground(new Color(186, 255, 133).darker());
                this.name.setText("Taken");
                if (apps.get(0).getTaken().equals(user.getLastname())) {
                    this.add(btnDelete);
                    this.add(btnEdit);
                }
            }
        } else if (apps.size() > 1) {
            for (int i = 0; i < apps.size(); i++) {
                if (apps.get(i).getTaken().equals("NOT_TAKEN")) {
                    this.setBackground(new Color(186, 255, 133));
                    String temp = this.name.getText() + " Dr. " + apps.get(i).getName();
                    this.name.setText(temp);
                } else {
                    this.setBackground(new Color(186, 255, 133).darker());
                    if (apps.get(i).getTaken().equals(user.getLastname())) {
                        this.app = apps.get(i);
                        this.name.setText("Taken");
                        this.add(btnDelete);
                        this.add(btnEdit);
                    }
                    break;
                }
            }
        }

        this.time.setText(time);
    }
    // CLIENT ENDS HERE

    // DOCTOR
    public ScheduleItem(ModuleController c, String time, Appointment app) {
        this();
        this.controller = c;
        this.setBackground(Color.white);
        this.app = app;
        if (app.getTaken().equals("NOT_TAKEN")) {
            this.setBackground(new Color(186, 255, 133));
            this.name.setText("FREE SLOT");
            this.add(btnDelete);
            this.add(btnEdit);

        } else {
            this.setBackground(new Color(186, 255, 133).darker());
            this.name.setText("APPOINTMENT W/ " + app.getTaken());
        }

        this.time.setText(time);
    }
    // DOCTOR ENDS HERE

    // SECRETARY
    public ScheduleItem(ModuleController c, String time, List<Appointment> apps) {
        this();
        
        this.controller = c;
        for(int i = 0; i < apps.size(); i++){
                    this.app = apps.get(i);
                    if (apps.get(0).getTaken().equals("NOT_TAKEN")) {
                        this.setBackground(new Color(186, 255, 133));
                        this.name.setText("Dr. " + apps.get(0).getName() + ": FREE SLOT");
                        
                        for(int j = 0; j < apps.size(); j++)
                        {
                            if(apps.get(j).getDate().equals(apps.get(i).getDate())
                               && apps.get(j).getTime() == apps.get(i).getTime()
                               && !(apps.get(j).getName().equals(apps.get(i).getName())))
                            {
                                this.name.setText("Dr. " + apps.get(i).getName() +
                                                  ", Dr. " + apps.get(j).getName() + ": FREE SLOT");
                            }
                        }
                        
                        this.add(btnDelete); 

                    } else {
                        this.setBackground(new Color(255, 100, 100));
                        this.name.setText("Dr." + apps.get(0).getName() + " : " + apps.get(0).getTaken());
                    }
                }
                this.time.setText(time);    
    }
    // SECRETARY ENDS HERE

    // GENERIC SLOT
    public ScheduleItem(ModuleController c, String time) {
        this();
        this.controller = c;
        this.setBackground(Color.white);
        this.time.setText(time);
        this.name.setText("");
    }

    class btnEdit_Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (controller instanceof DoctorController) {
                ((DoctorController) controller).edit(time.getText());
            } else if (controller instanceof ClientController) {
                ((ClientController) controller).edit(time.getText(),app.getName());
            }
        }
    }

    class btnDelete_Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (controller instanceof DoctorController) {
                ((DoctorController) controller).deleteAppointment(app);
            } else if (controller instanceof ClientController) {
                ((ClientController) controller).deleteAppointment(app);
            } else if (controller instanceof SecretaryController){
                ((SecretaryController) controller).deleteAppointment(app);
            }
            
        }
    }
}
