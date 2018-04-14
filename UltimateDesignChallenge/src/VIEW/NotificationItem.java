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
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ianona
 */
public class NotificationItem extends JPanel{
    private ModuleController controller;
    private Notification me;
    
    //DOCTOR
    private JButton btnDelete;
    private JLabel date;
    private JLabel msg;
    private static int turn = 0;

    public NotificationItem() {
        date = new JLabel();
        msg = new JLabel();
        
        btnDelete = new JButton();
        btnDelete.addActionListener(new btnDelete_Action());
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("RESOURCES/btnDelete.png")));
            btnDelete.setIcon(new ImageIcon(icon.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT)));
        } catch(IOException e) {
            System.out.println("FILE NOT FOUND");
        }

        this.add(date);
        this.add(msg);
        this.add(btnDelete);

        setPreferredSize(new Dimension(200, 45));
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
    }

    // DOCTOR
    public NotificationItem(ModuleController c, Notification notif) {
        this();
        this.controller = c;
        this.me = notif;
        if (turn%2==0)
            this.setBackground(Color.white);
        else
            this.setBackground(Color.LIGHT_GRAY);
        this.date.setText(notif.getDate());
        this.msg.setText(notif.getMessage());
        turn++;
    }
    
    class btnDelete_Action implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            ((DoctorController)controller).deleteNotif(me);
        }
    }
    
}
