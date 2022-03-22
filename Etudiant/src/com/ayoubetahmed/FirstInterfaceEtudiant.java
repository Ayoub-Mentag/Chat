package com.ayoubetahmed;


import com.ayoubetahmed.ui.etudiant.Etudiant_API;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class FirstInterfaceEtudiant {

    private JFrame frame;
    public static Etudiant_API etudiant_api = new Etudiant_API();
    public static final String IP = "192.168.1.43";
    public static final int PORT = 4444;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        etudiant_api.connectToServer(IP , PORT);
         showWindow();
    }

    public static void showWindow() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FirstInterfaceEtudiant window = new FirstInterfaceEtudiant();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }




    public FirstInterfaceEtudiant() throws IOException {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 730, 489);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);


        JLabel lbl = new JLabel("Espace d'étudiant");
        lbl.setBounds(200, 40 ,400, 100);
        lbl.setFont(new Font("Calibri", Font.BOLD, 20));
        frame.getContentPane().add(lbl);

        JButton btnLogin = new JButton("Se connecter");
        btnLogin.setBackground(Color.BLUE);
        btnLogin.setForeground(Color.MAGENTA);
        btnLogin.setBounds(150, 200, 120, 23);
        frame.getContentPane().add(btnLogin);


        JButton btnSignUp = new JButton("S'inscrire");
        btnSignUp.setBackground(Color.BLUE);
        btnSignUp.setForeground(Color.MAGENTA);
        btnSignUp.setBounds(300, 200, 100, 23);
        frame.getContentPane().add(btnSignUp);


        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    frame.setVisible(false);
                    FormInterface.showWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                    frame.setVisible(false);
                    LogInInterface.showWindow();
            }
        });

    }
}

