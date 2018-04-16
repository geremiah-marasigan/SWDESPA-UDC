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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
    
    // EVERYONE
    public AgendaItem(ModuleController c, Appointment app) {
        this();
        this.controller = c;
        this.app = app;
        
        if (controller instanceof DoctorController) {
            if (app.getTaken().equals("NOT_TAKEN")) {
                this.setBackground(new Color(186, 255, 133));
                appLbl.setText("FREE SLOT");
                trashBtn.setVisible(true);
            } else {
                this.setBackground(new Color(186, 255, 133).darker());
                appLbl.setText("APPOINTMENT W/ " + app.getTaken());
                trashBtn.setVisible(false);
            }
            timeLbl.setText("" + app.getTime());
        } else if (controller instanceof SecretaryController) {
            if (app.getTaken().equals("NOT_TAKEN")) {
                this.setBackground(new Color(186, 255, 133));
                appLbl.setText("FREE SLOT: " + app.getName());
                trashBtn.setVisible(true);
            } else {
                this.setBackground(new Color(255, 100, 100));
                appLbl.setText("Dr. " + app.getName() + " w/" + app.getTaken());
                trashBtn.setVisible(false);
            }
            timeLbl.setText("" + app.getTime());
        } else if (controller instanceof ClientController) {
            this.setBackground(new Color(186, 255, 133));
            appLbl.setText("Dr. "+app.getName());
            trashBtn.setVisible(false);
            timeLbl.setText("" + app.getTime());
        }
    }
    
    public static final AgendaItem createEmptyDoctor() {
        AgendaItem item = new AgendaItem();
        item.appLbl.setText("NOTHING FOR TODAY");
        item.setBackground(new Color(186, 184, 183));
        return item;
    }
    
    public static final AgendaItem createTitle(String text) {
        AgendaItem item = new AgendaItem();
        item.appLbl.setText(text);
        item.setBackground(new Color(186, 184, 183));
        return item;
    }

    // CLIENT
    public static final AgendaItem createEmptyClient() {
        AgendaItem item = new AgendaItem();
        item.appLbl.setText("NO APPOINTMENTS TODAY");
        item.setBackground(new Color(186, 184, 183));
        return item;
    }
    // CLIENT ENDS HERE

    // EVERYONE
    class trashBtn_Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (controller instanceof DoctorController) {
                if (okayToDelete(app))
                    ((DoctorController) controller).deleteDay(app);
                else
                    JOptionPane.showMessageDialog(((JButton)e.getSource()).getParent(), "Error! Slot taken, cannot delete entire interval.");
            } else if (controller instanceof ClientController) {
                /*
                if (app.getRepeat() == "None") {
                    ((ClientController) controller).deleteAppointment(app);
                } else {
                    ((ClientController) controller).deleteRepeat(app, ((ClientController) controller).getCurDate());
                }
                 */
            } else if (controller instanceof SecretaryController) {
                //((SecretaryController) controller).deleteAppointment(app);
            }
        }
        
        public boolean okayToDelete(Appointment app) {
            List<Appointment> taken = ((DoctorController) controller).getSlots(app.getDate(), ((DoctorController)controller).getUser());
            for (int i = 0; i < taken.size(); i++) {
                if (!taken.get(i).getTaken().equals("NOT_TAKEN"))
                    return false;
            }
            return true;
        }
    }
}
