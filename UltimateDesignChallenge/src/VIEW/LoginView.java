/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import CONTROLLER.LoginController;
import MODEL.User;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author ianona
 */
public class LoginView extends JFrame{
    private LoginController controller;
    
    private Container mainPane;
    private JTextField emailTxt;
    private JTextField passTxt;
    private JLabel emailLbl;
    private JLabel passLbl;
    private JButton submitBtn;
    private JButton clearBtn;
    private JLabel loginIcon;
    private JLabel errorLbl;
    
    public LoginView() {
        this.setTitle("Login");
        initGUI();
        this.setVisible(true);
    }
    
    public void attach (LoginController controller) {
        this.controller = controller;
    }
    
    public void initGUI() {
        this.setSize(600,280);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        mainPane = this.getContentPane();
        mainPane.setLayout(null);
        
        emailLbl = new JLabel("Email: ");
        passLbl = new JLabel("Password: ");
        emailTxt = new JTextField(15);
        passTxt = new JTextField(15);
        submitBtn = new JButton("Submit");
        clearBtn = new JButton("Clear");
        errorLbl = new JLabel();
        
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("RESOURCES/login.png")));
            loginIcon = new JLabel (new ImageIcon(icon.getImage().getScaledInstance(120,120, Image.SCALE_DEFAULT)));
        } catch(IOException e) {
            System.out.println("FILE NOT FOUND");
        }
        
        mainPane.add(errorLbl);
        mainPane.add(emailLbl);
        mainPane.add(emailTxt);
        mainPane.add(passLbl);
        mainPane.add(passTxt);
        mainPane.add(submitBtn);
        mainPane.add(clearBtn);
        mainPane.add(loginIcon);
        
        errorLbl.setBounds(150, 190, 200, 30);
        loginIcon.setBounds(250,20,100,100);
        emailLbl.setBounds(200, 130, 200, 30);
        emailTxt.setBounds(300, 130, 100, 30);
        passLbl.setBounds(200, 160, 200, 30);
        passTxt.setBounds(300, 160, 100, 30);
        submitBtn.setBounds(300,190,70,30);
        clearBtn.setBounds(300,215,70,30);
        
        submitBtn.addActionListener(new submitBtn_Action());
        clearBtn.addActionListener(new clearBtn_Action());
        
        errorLbl.setForeground(Color.red);
        errorLbl.setVisible(false);
        mainPane.setBackground(Color.white);
    }
    
    class submitBtn_Action implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            List<User> users = controller.getAll();
            String email = emailTxt.getText();
            String pass = passTxt.getText();
            
            boolean found = false;
            User foundUser = null;
            
            // DEBUGGING PURPOSES
            if (email.equalsIgnoreCase("debugD")) {
                found = true;
                controller.setBuilder(new DoctorBuilder());
            }
            else if (email.equalsIgnoreCase("debugS")) {
                found = true;
                controller.setBuilder(new SecretaryBuilder());
            }
            else if (email.equalsIgnoreCase("debugC")) {
                found = true;
                controller.setBuilder(new ClientBuilder());
            }
            
            for (User user : users) {
                if (user.getEmail().equals(email) && user.getPassword().equals(pass)) {
                    found = true;
                    foundUser = user;
                    switch (user.getType()) {
                        case "DOCTOR":
                            controller.setBuilder(new DoctorBuilder());
                            break;
                        case "SECRETARY":
                            controller.setBuilder(new SecretaryBuilder());
                            break;
                        case "CLIENT":
                            controller.setBuilder(new ClientBuilder());
                            break;
                    }
                }
            }
            
            if (!found) {
                errorLbl.setText("INVALID CREDENTIALS");
                errorLbl.setVisible(true);
            }
            else {
                emailTxt.setText("");
                passTxt.setText("");
                errorLbl.setVisible(false);
                controller.constructModule(foundUser);
                // setVisible(false);
            }
        }
    }
    
    class clearBtn_Action implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            emailTxt.setText("");
            passTxt.setText("");
        }
    }
}
