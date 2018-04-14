/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import CONTROLLER.*;
import MODEL.*;
import java.awt.Color;
import java.awt.Container;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 *
 * @author ianona
 */
public class WeeklyView extends JFrame {
    private ModuleController controller;
    private Container mainPane;
    private ScheduleView mView, tView, wView, hView, fView, stView, suView;
    private JScrollPane mScroll, tScroll, wScroll, hScroll, fScroll, stScroll, suScroll;
    private List<String[]> weekInfo;
    private User user;
    
    public WeeklyView (ModuleController c, List<String[]> weekInfo, User user) {
        this.controller = c;
        this.weekInfo = weekInfo;
        this.user = user;
        this.setTitle("Weekly View");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(1260,650);
        
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
        
        suView = new ScheduleView(controller);
        mView = new ScheduleView(controller);
        tView = new ScheduleView(controller);
        wView = new ScheduleView(controller);
        hView = new ScheduleView(controller);
        fView = new ScheduleView(controller);
        stView = new ScheduleView(controller);
        
        suScroll = new JScrollPane(suView);
        mScroll = new JScrollPane(mView);
        tScroll = new JScrollPane(tView);
        wScroll = new JScrollPane(wView);
        hScroll = new JScrollPane(hView);
        fScroll = new JScrollPane(fView);
        stScroll = new JScrollPane(stView);
        
        suScroll.setBounds(0,0,180,630);
        mScroll.setBounds(180,0,180,630);
        tScroll.setBounds(360,0,180,630);
        wScroll.setBounds(540,0,180,630);
        hScroll.setBounds(720,0,180,630);
        fScroll.setBounds(900,0,180,630);
        stScroll.setBounds(1080,0,180,630);
        
        add(suScroll);
        add(mScroll);
        add(tScroll);
        add(wScroll);
        add(hScroll);
        add(fScroll);
        add(stScroll);
        
        update(weekInfo);
        
        suScroll.getVerticalScrollBar().setUnitIncrement(10);
        mScroll.getVerticalScrollBar().setUnitIncrement(10);
        tScroll.getVerticalScrollBar().setUnitIncrement(10);
        wScroll.getVerticalScrollBar().setUnitIncrement(10);
        hScroll.getVerticalScrollBar().setUnitIncrement(10);
        fScroll.getVerticalScrollBar().setUnitIncrement(10);
        stScroll.getVerticalScrollBar().setUnitIncrement(10);
    }
    
    public void update (List<String[]> weekInfo) {
        this.weekInfo = weekInfo;
        List<Appointment>apps = ((DoctorController)controller).getAllSlots();
        suView.setItems(apps,(weekInfo.get(0)[0] + "/"+ weekInfo.get(0)[1] +"/"+ weekInfo.get(0)[2]),user);
        suScroll.setBorder(BorderFactory.createTitledBorder("Sun: "+ weekInfo.get(0)[0] + "/"+ weekInfo.get(0)[1] +"/"+ weekInfo.get(0)[2]));
        mView.setItems(apps,(weekInfo.get(1)[0] + "/"+ weekInfo.get(1)[1] +"/"+ weekInfo.get(1)[2]),user);
        mScroll.setBorder(BorderFactory.createTitledBorder("Mon: "+ weekInfo.get(1)[0] + "/"+ weekInfo.get(1)[1] +"/"+ weekInfo.get(1)[2]));
        tView.setItems(apps,(weekInfo.get(2)[0] + "/"+ weekInfo.get(2)[1] +"/"+ weekInfo.get(2)[2]),user);
        tScroll.setBorder(BorderFactory.createTitledBorder("Tue: "+ weekInfo.get(2)[0] + "/"+ weekInfo.get(2)[1] +"/"+ weekInfo.get(2)[2]));
        wView.setItems(apps,(weekInfo.get(3)[0] + "/"+ weekInfo.get(3)[1] +"/"+ weekInfo.get(3)[2]),user);
        wScroll.setBorder(BorderFactory.createTitledBorder("Wed: "+ weekInfo.get(3)[0] + "/"+ weekInfo.get(3)[1] +"/"+ weekInfo.get(3)[2]));
        hView.setItems(apps,(weekInfo.get(4)[0] + "/"+ weekInfo.get(4)[1] +"/"+ weekInfo.get(4)[2]),user);
        hScroll.setBorder(BorderFactory.createTitledBorder("Thu: "+ weekInfo.get(4)[0] + "/"+ weekInfo.get(4)[1] +"/"+ weekInfo.get(4)[2]));
        fView.setItems(apps,(weekInfo.get(5)[0] + "/"+ weekInfo.get(5)[1] +"/"+ weekInfo.get(5)[2]),user);
        fScroll.setBorder(BorderFactory.createTitledBorder("Fri: "+ weekInfo.get(5)[0] + "/"+ weekInfo.get(5)[1] +"/"+ weekInfo.get(5)[2]));
        stView.setItems(apps,(weekInfo.get(6)[0] + "/"+ weekInfo.get(6)[1] +"/"+ weekInfo.get(6)[2]),user);
        stScroll.setBorder(BorderFactory.createTitledBorder("Sat: "+ weekInfo.get(6)[0] + "/"+ weekInfo.get(6)[1] +"/"+ weekInfo.get(6)[2]));

        revalidate();
        repaint();
    }
}
