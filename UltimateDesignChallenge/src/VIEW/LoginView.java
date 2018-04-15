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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author ianona
 */
public class LoginView extends JFrame{
    private LoginController controller;
    
    private Container mainPane;
    private JTextField emailTxt;
    private JPasswordField passTxt;
    private JLabel emailLbl;
    private JLabel passLbl;
    private JButton submitBtn;
    private JButton exitBtn;
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
        this.setSize(600,295);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        mainPane = this.getContentPane();
        mainPane.setLayout(null);
        this.setLocationRelativeTo(null);
        
        emailLbl = new JLabel("Email: ");
        passLbl = new JLabel("Password: ");
        emailTxt = new JTextField(15);
        passTxt = new JPasswordField(15);
        submitBtn = new JButton("Submit");
        exitBtn = new JButton("Exit");
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
        mainPane.add(exitBtn);
        
        errorLbl.setBounds(150, 190, 200, 30);
        loginIcon.setBounds(250,20,100,100);
        emailLbl.setBounds(200, 130, 200, 30);
        emailTxt.setBounds(300, 130, 100, 30);
        passLbl.setBounds(200, 160, 200, 30);
        passTxt.setBounds(300, 160, 100, 30);
        submitBtn.setBounds(260,190,100,30);
        clearBtn.setBounds(260,215,100,30);
        exitBtn.setBounds(260,240,100,30);
        
        submitBtn.addActionListener(new submitBtn_Action());
        clearBtn.addActionListener(new clearBtn_Action());
        exitBtn.addActionListener(new exitBtn_Action());
        emailTxt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                emailTxt.setCaretPosition(0);
            }
        });
        passTxt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                passTxt.setCaretPosition(0);
            }
        });
        
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
                foundUser = new User("zach_marasigan@dlsu.edu.ph", "zach", "DOCTOR", "Zach", "Marasigan");
            }
            else if (email.equalsIgnoreCase("debugS")) {
                found = true;
                foundUser = new User("won_suk_cho@dlsu.edu.ph", "wonsuk", "SECRETARY", "Won Suk", "Cho");
            }
            else if (email.equalsIgnoreCase("debugC")) {
                found = true;
                foundUser = new User("charles_navarro@dlsu.edu.ph", "charles", "CLIENT", "Charles", "Navarro");
            }
            
            for (User user : users) {
                if (user.getEmail().equals(email) && user.getPassword().equals(pass)) {
                    found = true;
                    foundUser = user;
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
    
    class exitBtn_Action implements ActionListener{
        @Override
        public void actionPerformed (ActionEvent e){
            System.exit(0);
        }
    }
}
