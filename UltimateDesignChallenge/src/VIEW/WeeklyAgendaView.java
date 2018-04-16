/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import CONTROLLER.ModuleController;
import MODEL.User;
import java.awt.Color;
import java.awt.Container;
import java.util.List;
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
        update(weekInfo);
        
        aScroll.getVerticalScrollBar().setUnitIncrement(10);
    }
    
    public void update(List<String[]> weeklyInfo) {
        aView.setWeeklyItems(weekInfo);
    }
}
