package com.ayoubetahmed;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InterfaceTableAdmin{
    JFrame frame;
    JTable j;

    public  void showWindow()  {
        initialize();
        frame.setVisible(true);
        }

        public void initialize() {
            frame = new JFrame();
            frame.setTitle("JTable Example");

            JButton btnBack = new JButton("Retour");
            btnBack.setBackground(Color.BLUE);
            btnBack.setForeground(Color.MAGENTA);
            btnBack.setBounds(10, 100, 90, 23);
            frame.getContentPane().add(btnBack);
            btnBack.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                        frame.setVisible(false);
                        new LoginAdmin().showWindow();

                }
            });


            JButton btnRefresh = new JButton("Actualiser");
            btnRefresh.setBackground(Color.BLUE);
            btnRefresh.setForeground(Color.MAGENTA);
            btnRefresh.setBounds(120, 100, 105, 23);
            frame.getContentPane().add(btnRefresh);
            btnRefresh.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
                    initialize();
                }
            });


            //Tableau des étudiant pas activé ou pas approuvé
            String[] columnNames = { "id Etudiant" , "first name ","last name" , "Active", "Approuvé" };
            String[][] data =  LoginAdmin.adminAPI.getStudentWantToBeActivatedOrApproved();
            if (data == null){
                data = new String[][]{};
            }
            j = new JTable(data, columnNames);
            j.setBounds(30, 100, 200, 300);
            j.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                            frame.setVisible(false);
                        //System.out.println(j.getValueAt(j.getSelectedRow() , 0));
                        int id = Integer.parseInt(j.getValueAt(j.getSelectedRow() , 0).toString());
                        String firstName = j.getValueAt(j.getSelectedRow() , 1).toString();
                        String lastName = j.getValueAt(j.getSelectedRow() , 2).toString();
                        String isActivated = j.getValueAt(j.getSelectedRow(), 3).toString();
                        String isApproved = j.getValueAt(j.getSelectedRow(), 4).toString();
                        new InfoInterface(id , firstName , lastName , isActivated , isApproved).showWindow();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
            JScrollPane sp = new JScrollPane(j);
            frame.add(sp);


            frame.setBounds(100, 100, 730, 489);
            frame.setVisible(true);
        }

    }

