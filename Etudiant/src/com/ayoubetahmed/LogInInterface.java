package com.ayoubetahmed;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInInterface {
    private JFrame frame;
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    private static String pseudo = "test";

    public static void showWindow() {
        LogInInterface window = new LogInInterface();
        window.frame.setVisible(true);
    }

    public LogInInterface() {
        initialize();
    }


    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 730, 489);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lbl = new JLabel("Espace d'étudiant");
        lbl.setBounds(200, 40 ,400, 100);
        lbl.setFont(new Font("Calibri", Font.BOLD, 20));
        frame.getContentPane().add(lbl);

        //email
        JLabel lblEmail = new JLabel("Email");
        lblEmail.setBounds(65, 155, 46, 14);
        frame.getContentPane().add(lblEmail);


        txtUsername = new JTextField();
        txtUsername.setBounds(128, 155, 247, 17);
        frame.getContentPane().add(txtUsername);
        txtUsername.setColumns(10);

        //password
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(65, 195, 60, 14);
        frame.getContentPane().add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(128, 195, 247, 17);
        frame.getContentPane().add(txtPassword);
        txtPassword.setColumns(10);


        JButton btnLogin = new JButton("Se connecter");

        btnLogin.setBackground(Color.BLUE);
        btnLogin.setForeground(Color.MAGENTA);
        btnLogin.setBounds(65, 387, 89, 23);
        frame.getContentPane().add(btnLogin);


        JButton btnBack = new JButton("<--");

        btnBack.setBackground(Color.BLUE);
        btnBack.setForeground(Color.MAGENTA);
        btnBack.setBounds(10, 10, 60, 23);
        frame.getContentPane().add(btnBack);

        JLabel lblMsg = new JLabel();
        lblMsg.setBounds(65, 300 ,400, 14);
        frame.getContentPane().add(lblMsg);


        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                FirstInterfaceEtudiant.showWindow();
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(txtUsername.getText().isEmpty()||(txtUsername.getText().isEmpty())){
                    JOptionPane.showMessageDialog(null, "Data Missing");
                }
                else {
                    //TODO change it to boolean
                    String result = FirstInterfaceEtudiant.etudiant_api.login(txtUsername.getText() , txtPassword.getText());
                    String[] elements = result.split(":");
                    switch (elements[0]){
                        case "-1":
                            JOptionPane.showMessageDialog(null, "Info are incorrect");
                            break;
                        case "-2":
                            JOptionPane.showMessageDialog(null, "You need to be activated from the Admin");
                             break;
                        default:
                            frame.setVisible(false);
                            int id = Integer.parseInt(elements[0]);
                            String pseudo = elements[1];
                            new EtudiantMainInterface(id , pseudo).showWindow();
                            break;
                    }
                }

            }
        });


    }





}
