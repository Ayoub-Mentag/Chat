package com.ayoubetahmed;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrivateChat {

        private int id ;
        private int id_friend;
        private String friend;
        private JFrame frame ;
        private String pseudo_me;

        public PrivateChat(String me ,  String friend , int id , int id_friend) {
            this.pseudo_me = me;
            this.id = id;
            this.id_friend = id_friend ;
            this.friend = friend;
        }

        public  void showWindow() {
            initialize();
            frame.setVisible(true);
        }

        private void initialize() {
            frame = new JFrame("frame");
            frame.setBounds(500, 100, 730, 489);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setLayout(null);

            JButton btnBack = new JButton("Retour");
            btnBack.setBounds(10, 10, 100, 23);
            frame.getContentPane().add(btnBack);
            btnBack.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                      frame.setVisible(false);
                      new EtudiantMainInterface(id , pseudo_me).showWindow();
                }
            });

            JLabel label = new JLabel("Chat privé avec " + this.friend);
            label.setBounds(30 , 60 , 400 , 20);
            frame.add(label);
            String titre = "Bonjour "+this.pseudo_me;
            JLabel lblRecipient = new JLabel(titre);
            lblRecipient.setBounds(10 , 40 , 200 , 20);
            frame.add(lblRecipient);

            JTextArea area=new JTextArea();
            area.setBounds(20,90, 650,300);
            area.setEditable(false);
            area.setText(getPrivateMessages(id, id_friend));
            frame.add(area);

            JTextField textField = new JTextField();
            textField.setBounds(20 , 400 , 300 , 30);
            frame.add(textField);

            JButton btnRefresh = new JButton("Actualiser");
            btnRefresh.setBounds(20, 440, 100, 23);
            frame.getContentPane().add(btnRefresh);
            btnRefresh.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
                    initialize();
                }
            });

            JButton btnSend = new JButton("Envoyer");
            btnSend.setBounds(160, 440, 100, 23);
            frame.getContentPane().add(btnSend);
            btnSend.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String message = textField.getText().toString();
                    //friend is the name -pseudo- of the recipient
                    //id is the id of the sender
                    //message is the message to be sent
                    int result = FirstInterfaceEtudiant.etudiant_api.sendMessage(id , id_friend, message);
                    switch (result){
                        case 1 :
                            textField.setText(null);
                            break;
                        case 0 :
                            JOptionPane.showMessageDialog(null , "Le message a été envoyé au admin pour l'approuver");
                            break;
                        case -1 :
                            JOptionPane.showMessageDialog(null , "Il y a une erreur");
                            break;
                        default:
                            break;
                    }
                }
            });

            frame.setSize(700,550);
            frame.setLayout(null);
            frame.setVisible(true);
            frame.show();

        }

    private String getPrivateMessages(int id, int id_friend) {
         return FirstInterfaceEtudiant.etudiant_api.getMessagesPrivate(id , id_friend);
        }

}
