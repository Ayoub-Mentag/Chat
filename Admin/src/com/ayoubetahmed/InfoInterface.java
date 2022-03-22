package com.ayoubetahmed;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InfoInterface {
    private String firstName , lastName;
    private String  isActivated , isApproved;
    private JFrame frame;
    private JTable j;
    private int id;
    public InfoInterface(int id , String firstName , String lastName , String isActivated , String isApproved) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActivated = isActivated;
        this.isApproved = isApproved;
    }

    private void initialize(){
        //id
        //full name
        //active && approve
        //messages
        //btn pour activer & approuver
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 730);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);



        //Label pour le nom complet
        JLabel lblFullName = new JLabel("Nom Complet");
        lblFullName.setBounds(65, 50, 100, 14);
        frame.getContentPane().add(lblFullName);

        JLabel lblFullNameV = new JLabel(firstName + " " + lastName);
        lblFullNameV.setBounds(200, 50, 200, 14);
        frame.getContentPane().add(lblFullNameV);


        JLabel lblMsg = new JLabel("Les messages");
        lblMsg.setBounds(65, 100, 100, 14);
        frame.getContentPane().add(lblMsg);

        //Tableau contient les messages de chaque étudiant
       String[][] data =  LoginAdmin.adminAPI.getMessagesOfASpecificStudent(id);
        if (data == null){
            data = new String[][]{{"Il y a aucun message de " ,  lblFullNameV.getText() }};
        }
        String[] columnNames = { "messages" , "type"};
        j = new JTable(data, columnNames);
        j.setBounds(30, 130, 500, 300);
        frame.add(j);


        JLabel lblActive = new JLabel("Active");
        lblActive.setBounds(65, 450, 100, 14);
        frame.getContentPane().add(lblActive);

        JLabel lblActiveV = new JLabel(isActivated);
        lblActiveV.setBounds(120, 450, 46, 14);
        frame.getContentPane().add(lblActiveV);

        JLabel lblApprove = new JLabel("Approve");
        lblApprove.setBounds(65, 500, 100, 14);
        frame.getContentPane().add(lblApprove);

        JLabel lblApproveV = new JLabel(isApproved);
        lblApproveV.setBounds(120, 500, 46, 14);
        frame.getContentPane().add(lblApproveV);


        JButton btnActive = new JButton("Activer");
        btnActive.setBackground(Color.BLUE);
        btnActive.setForeground(Color.MAGENTA);
        btnActive.setBounds(200, 450, 130, 23);
        frame.getContentPane().add(btnActive);

        JButton btnApprove = new JButton("Approuver");
        btnApprove.setBackground(Color.BLUE);
        btnApprove.setForeground(Color.MAGENTA);
        btnApprove.setBounds(200, 500, 130, 23);
        frame.getContentPane().add(btnApprove);

        JButton btnBack = new JButton("Retour");
        btnBack.setBackground(Color.BLUE);
        btnBack.setForeground(Color.MAGENTA);
        btnBack.setBounds(200, 550, 130, 23);
        frame.getContentPane().add(btnBack);


        JButton btnRefresh = new JButton("Actualiser");
        btnRefresh.setBackground(Color.BLUE);
        btnRefresh.setForeground(Color.MAGENTA);
        btnRefresh.setBounds(200, 600, 130, 23);
        frame.getContentPane().add(btnRefresh);

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                showWindow();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
                    new InterfaceTableAdmin().showWindow();

            }
        });

        btnActive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblActiveV.setText("1");
                new LoginAdmin().adminAPI.activeAStudent(id);
               //send to the server to activate this student
            }
        });

        btnApprove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lblActiveV.getText().equals("0")){
                    lblActiveV.setText("1");
                    new LoginAdmin().adminAPI.activeAStudent(id);
                }
                lblApproveV.setText("1");
                new LoginAdmin().adminAPI.approveStudent(id);
            }
        });

    }

    public void showWindow() {
            initialize();
            frame.setVisible(true);
    }
}
