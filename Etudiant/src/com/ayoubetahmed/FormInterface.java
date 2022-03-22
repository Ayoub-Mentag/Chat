package com.ayoubetahmed;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class FormInterface {
    private JFrame frame;
    private JTextField txtNom;
    private JTextField txtPrenom;
    private JTextField txtPseudo;
    private JTextField txtUsername;
    private JPasswordField txtPassword;



    public static void showWindow() throws IOException {
                    FormInterface window = new FormInterface();
                    window.frame.setVisible(true);
    }



    public FormInterface() throws IOException {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 730, 489);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);



        //nom
        JLabel lblNom = new JLabel("Nom");
        lblNom.setBounds(65, 31, 46, 14);
        frame.getContentPane().add(lblNom);

        txtNom = new JTextField();
        txtNom.setBounds(128, 28, 86, 20);
        frame.getContentPane().add(txtNom);
        txtNom.setColumns(10);



        //prenom
        JLabel lblPrenom = new JLabel("Prenom");
        lblPrenom.setBounds(65, 68, 46, 14);
        frame.getContentPane().add(lblPrenom);

        txtPrenom = new JTextField();
        txtPrenom.setBounds(128, 65, 86, 20);
        frame.getContentPane().add(txtPrenom);
        txtPrenom.setColumns(10);

        //pseudo
        JLabel lblpseudo = new JLabel("Pseudo");
        lblpseudo.setBounds(65, 115, 46, 14);
        frame.getContentPane().add(lblpseudo);


        txtPseudo = new JTextField();
        txtPseudo.setBounds(128, 116, 247, 17);
        frame.getContentPane().add(txtPseudo);
        txtPseudo.setColumns(10);

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


        JButton btnClear = new JButton("Clear");
        btnClear.setBackground(Color.BLUE);
        btnClear.setForeground(Color.MAGENTA);
        btnClear.setBounds(200, 387, 89, 23);
        frame.getContentPane().add(btnClear);

        JButton btnSignUp = new JButton("S'inscrire");
        btnSignUp.setBackground(Color.BLUE);
        btnSignUp.setForeground(Color.MAGENTA);
        btnSignUp.setBounds(65, 387, 89, 23);
        frame.getContentPane().add(btnSignUp);

        JButton btnBack = new JButton("<--");

        btnBack.setBackground(Color.BLUE);
        btnBack.setForeground(Color.MAGENTA);
        btnBack.setBounds(10, 10, 60, 23);
        frame.getContentPane().add(btnBack);


        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                FirstInterfaceEtudiant.showWindow();
            }
        });

        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(txtNom.getText().isEmpty()||(txtPrenom.getText().isEmpty())||(txtPseudo.getText().isEmpty())||(txtUsername.getText().isEmpty()))
                {
                    JOptionPane.showMessageDialog(null, "Data Missing");
                }
                else {
                    //return 1 -> correcte
                    //return -1 -> changer le pseudo
                    //return -2 -> changer le username
                    String  result = FirstInterfaceEtudiant.etudiant_api.signUp(txtNom.getText() , txtPrenom.getText() , txtPseudo.getText() ,
                            txtUsername.getText() , txtPassword.getText());
                    switch (result){
                        case "1":
                            JOptionPane.showMessageDialog(null, "Vous étes inscris , bienvenue ");
                            txtNom.setText(null);
                            txtPrenom.setText(null);
                            txtPseudo.setText(null);
                            txtUsername.setText(null);
                            txtPassword.setText(null);
                            break;
                        case "-1":
                            JOptionPane.showMessageDialog(null, "Le pseudo que vous choisissez est déjà utilisé");
                            break;
                        case "-2":
                            JOptionPane.showMessageDialog(null, "Le username que vous choisissez est déjà utilisé");
                            break;
                        default:
                            break;
                    }
                 }
            }
        });


        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtNom.setText(null);
                txtPrenom.setText(null);
                txtPseudo.setText(null);
                txtUsername.setText(null);
                txtPassword.setText(null);
            }
        });

    }
}

