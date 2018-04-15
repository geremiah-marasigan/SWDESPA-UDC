/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import CONTROLLER.DoctorController;
import CONTROLLER.ModuleController;
import MODEL.Appointment;
import MODEL.User;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 *
 * @author ianona
 */
public class WeeklyAgendaView extends JFrame{
    private ModuleController controller;
    private Container mainPane;
    private AgendaView aView;
    private JScrollPane aScroll;
    private List<String[]> weekInfo;
    private User user;
    int curIndex;
    
    public WeeklyAgendaView (ModuleController c, List<String[]> weekInfo, User user) {
        this.controller = c;
        this.weekInfo = weekInfo;
        this.user = user;
        this.setTitle("Weekly Agenda View");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(420,650);
        
        initialize();
        
        this.setResizable(false);
        this.setVisible(true);
        this.revalidate();
        this.repaint();
    }
    
    public void initialize() {
        mainPane = this.getContentPane();
        mainPane.setLayout(null);
        mainPane.setBackground(Color.white);
        
        aView = new AgendaView(controller);
        aView.setUser(user);
        aScroll = new JScrollPane(aView);
        
        aScroll.setBounds(10,10,390,500);
        
        add(aScroll);
        
        curIndex = 0;
        aView.setWeeklyItems(weekInfo);
        
        aScroll.getVerticalScrollBar().setUnitIncrement(10);
    }
}
