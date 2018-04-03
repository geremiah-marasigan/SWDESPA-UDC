/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import CONTROLLER.DoctorController;
import MODEL.Appointment;
import MODEL.User;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.GregorianCalendar;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author ianona
 */
public class DoctorBuilder extends Builder {
    private JButton btnApp;
    private JPanel pnlApp;
    
    private JLabel sDayLbl, eDayLbl, sTimeLbl, eTimeLbl, repeatLbl;
    private JTextField sDay, eDay;
    private JComboBox sTime, eTime, repeat;
    private JButton btnAdd, btnCancel;
    
    @Override
    public void buildIcon() {
        try {
            ImageIcon imgicon = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("RESOURCES/doctor.png")));
            icon = new JLabel (new ImageIcon(imgicon.getImage().getScaledInstance(120,120, Image.SCALE_DEFAULT)));
        } catch(IOException e) {
            System.out.println("FILE NOT FOUND");
        }
        mainPane.add(icon);
        icon.setBounds(10,10,100,100);
    }

    @Override
    public void buildCustom(User user) {
        System.out.println("Building custom DOCTOR components...");
        module.setTitle("Doctor Module - Dr. " + user.getFirstname());
        this.user = user;
        module.setUser(user);
        
        btnApp = new JButton();
        btnApp.setBounds(10,380, 50, 50);
        btnApp.addActionListener(new btnApp_Action());
        mainPane.add(btnApp);
            
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("RESOURCES/btnApp.png")));
            btnApp.setIcon(new ImageIcon(icon.getImage().getScaledInstance(40,40, Image.SCALE_DEFAULT)));
        } catch(IOException e) {
            System.out.println("FILE NOT FOUND");
        }
        
        pnlApp = new JPanel(null);
        pnlApp.setBounds(220, 495, 390, 125);
        pnlApp.setBackground(Color.LIGHT_GRAY);
        mainPane.add(pnlApp);
        pnlApp.setVisible(false);
        
        btnAdd = new JButton("Set Slots");
        btnAdd.addActionListener(new btnAdd_Action());
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                pnlApp.setVisible(false);
            }
        });
        sDay = new JTextField(10);
        eDay = new JTextField(10);
        eDay.setVisible(false);
        sTime = new JComboBox();
        eTime = new JComboBox();
        for (int hour = 0; hour < 24; hour++) {
            for (int min = 0; min < 60; min += 30) {
                String hourString = String.valueOf(hour);
                if (hourString.length() == 1)
                    hourString = "0"+hourString;
                String minString = String.valueOf(min);
                if (minString.length() == 1)
                    minString = "0"+minString;
                String time = hourString + ":" + minString;
                sTime.addItem(time);
                eTime.addItem(time);
            }
        }
        String[] repeatOptions = {"None", "Daily", "Monthly"};
        repeat = new JComboBox(repeatOptions);
        repeat.setSelectedIndex(0);
        repeat.addActionListener(new repeat_Action());
        
        sDayLbl = new JLabel("Start Day: ");
        eDayLbl = new JLabel("End Day: ");
        eDayLbl.setVisible(false);
        sTimeLbl = new JLabel("Start Time: ");
        eTimeLbl = new JLabel("End Time: ");
        repeatLbl = new JLabel("Repeat: ");
        
        GregorianCalendar cal = new GregorianCalendar();
        int dayBound = cal.get(GregorianCalendar.DAY_OF_MONTH);
        int monthBound = cal.get(GregorianCalendar.MONTH) + 1;
        int yearBound = cal.get(GregorianCalendar.YEAR);
        String date = monthBound + "/" + dayBound + "/" + yearBound;
        setTextFields(date);
        
        sDayLbl.setBounds(5, 5, 100, 25);
        sTimeLbl.setBounds(5, 30, 100, 25);
        eTimeLbl.setBounds(5, 55, 100, 25);
        repeatLbl.setBounds(210, 5, 70, 25);
        eDayLbl.setBounds(210, 30, 70, 25);
        
        sDay.setBounds(105, 5, 100, 25);
        sTime.setBounds(105, 30, 100, 25);
        eTime.setBounds(105, 55, 100, 25);
        repeat.setBounds(280, 5, 100, 25);
        eDay.setBounds(280, 30, 100, 25);
        btnAdd.setBounds(280, 60, 100,30);
        btnCancel.setBounds(280, 90, 100,30);
        
        pnlApp.add(btnAdd);
        pnlApp.add(btnCancel);
        pnlApp.add(sDay);
        pnlApp.add(eDay);
        pnlApp.add(sTime);
        pnlApp.add(eTime);
        pnlApp.add(repeat);
        
        pnlApp.add(sDayLbl);
        pnlApp.add(eDayLbl);
        pnlApp.add(sTimeLbl);
        pnlApp.add(eTimeLbl);
        pnlApp.add(repeatLbl);
    }
    
    public void setTextFields(String date) {
        sDay.setText(date);
        eDay.setText(date);
    }
    
    class btnApp_Action implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            pnlApp.setVisible(true);
        }
    }
    
    class btnAdd_Action implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            String choice = String.valueOf(repeat.getSelectedItem());
            String startD = sDay.getText();
            String endD = null;
            if (choice.equalsIgnoreCase("None")) 
                endD = startD;
            else 
                endD = eDay.getText();
            int startT = Integer.parseInt(sTime.getSelectedItem().toString().replace(":", ""));
            int endT = Integer.parseInt(eTime.getSelectedItem().toString().replace(":", ""));
            
            ((DoctorController)module.controller).addAppointment(new Appointment(user.getFirstname(), startD, endD, choice, startT, endT));
            pnlApp.setVisible(false);
        } 
    }
    
    class repeat_Action implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            String choice = String.valueOf(repeat.getSelectedItem());
            if (choice.equalsIgnoreCase("None")) {
                eDay.setVisible(false);
                eDayLbl.setVisible(false);
            }
            else {
                eDay.setVisible(true);
                eDayLbl.setVisible(true);
            }
                
        }
    }
}
