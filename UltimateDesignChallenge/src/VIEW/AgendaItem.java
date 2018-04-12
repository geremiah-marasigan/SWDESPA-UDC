/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import CONTROLLER.ClientController;
import CONTROLLER.DoctorController;
import CONTROLLER.ModuleController;
import CONTROLLER.SecretaryController;
import MODEL.Appointment;
import MODEL.User;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ianona
 */
public class AgendaItem extends JPanel {

    private Appointment app;
    private ModuleController controller;

    private JLabel appLbl, timeLbl, nameLbl;
    private JButton trashBtn;

    public AgendaItem() {
        appLbl = new JLabel();
        timeLbl = new JLabel();
        nameLbl = new JLabel();
        trashBtn = new JButton();
        trashBtn.addActionListener(new trashBtn_Action());

        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("RESOURCES/btnTrash.png")));
            trashBtn.setIcon(new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        } catch (IOException e) {
            System.out.println("FILE NOT FOUND");
        }

        appLbl.setPreferredSize(new Dimension(180, 40));
        timeLbl.setPreferredSize(new Dimension(80, 40));

        add(appLbl);
        add(nameLbl);
        add(timeLbl);
        add(trashBtn);
        trashBtn.setVisible(false);

        setBorder(BorderFactory.createLineBorder(Color.white, 1));
        setPreferredSize(new Dimension(390, 40));
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
    }

    public AgendaItem(ModuleController c, Appointment app) {
        this();
        this.controller = c;
        this.app = app;
        this.setBackground(new Color(186, 255, 133));

        if (controller instanceof DoctorController) {
            appLbl.setText("FREE APPOINTMENT SLOT");
            timeLbl.setText("" + app.getStartTime() + " - " + app.getEndTime());
            trashBtn.setVisible(true);
        } else if (controller instanceof ClientController) {
            if (app.getStartDay().equals(app.getEndDay())) {
                appLbl.setText(app.getStartDay());
            } else {
                appLbl.setText(app.getStartDay() + " - " + app.getEndDay());
            }
            timeLbl.setText(app.getStartTime() + " - " + app.getEndTime());
            List<Appointment> apps = ((ClientController) controller).getAllAppointments();
            List<User> users = ((ClientController) controller).getAllUsers();
            if (app.getStartTime() >= app.getEndTime()) {
                
            }
            
                for (Appointment App : apps) {
                    for (User user : users) {
                        if (App.getName().equals(user.getFirstname()) && user.getType().equals("DOCTOR")) {
                            if (app.getStartTime() >= App.getStartTime() && app.getEndTime() <= App.getEndTime()) {
                                nameLbl.setText("Dr. " + App.getName());
                            }
                        }

                    }
                }
            trashBtn.setVisible(true);
        } else if (controller instanceof SecretaryController) {
            if (app.getStartDay().equals(app.getEndDay())) {
                System.out.println(app.getStartDay());
                appLbl.setText(app.getStartDay());
            } else {
                appLbl.setText(app.getStartDay() + " - " + app.getEndDay());
            }
            trashBtn.setVisible(true);
            timeLbl.setText(app.getStartTime() + " - " + app.getEndTime());
            if (((SecretaryController) controller).getAppointmentType(app).equals("DOCTOR")) {
                nameLbl.setText("Dr. " + app.getName());
            } else {
                nameLbl.setText(app.getName());
                this.setBackground(Color.CYAN);
            }
        }
    }

    public static final AgendaItem createEmptyDoctor() {
        AgendaItem item = new AgendaItem();
        item.appLbl.setText("NOTHING FOR TODAY");
        item.setBackground(new Color(186, 184, 183));
        return item;
    }

    public static final AgendaItem createEmptyClient() {
        AgendaItem item = new AgendaItem();
        item.appLbl.setText("NO APPOINTMENTS TODAY");
        item.setBackground(new Color(186, 184, 183));
        return item;
    }

    class trashBtn_Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (controller instanceof DoctorController) {
                ((DoctorController) controller).deleteAppointment(app);
            } else if (controller instanceof ClientController) {
                ((ClientController) controller).deleteAppointment(app);
            }
        }
    }
}
