package com.ayoubetahmed;

import com.ayoubetahmed.ui.etudiant.Etudiant_API;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EtudiantMainInterface {
    private JFrame frame ;
    private int id ;
    private String pseudo;

   private JTable j;

    public  void showWindow() {
        initialize();
        frame.setVisible(true);
    }

    public EtudiantMainInterface(int id , String pseudo)  {
        this.id = id;
        this.pseudo = pseudo;
    }

    private void initialize() {
        frame = new JFrame("frame");
        frame.setBounds(500, 100, 730, 489);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel label = new JLabel(pseudo);
        label.setBounds(30 , 30 , 100 , 20);
        frame.add(label);

        label = new JLabel("Online * ");
        label.setBounds(640 , 30 , 100 , 20);
        frame.add(label);

        String[][] data = FirstInterfaceEtudiant.etudiant_api.getConnectedStudents(this.id);
        String[] columnNames = { "" };

        j = new JTable(data, columnNames);
        j.setBounds(550, 50, 200, 200);
        j.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
               if (data !=null){
                    int id_friend = FirstInterfaceEtudiant.etudiant_api.getIdByPseudo(j.getValueAt(j.getSelectedRow() , 0).toString());
                    new PrivateChat(pseudo ,j.getValueAt(j.getSelectedRow() , 0).toString() , id , id_friend ).showWindow();
                    frame.setVisible(false);

                }
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
        frame.add(j);

        //ce variable contient tous les messages public
        String messages = getMessagesPublic();
        JTextArea area=new JTextArea();
        area.setBounds(20,50, 450,200);
        area.setEditable(false);
        area.setText(messages);
        frame.add(area);


        JTextField txtMessage=new JTextField();
        txtMessage.setBounds(20,300, 730,50);
        frame.add(txtMessage);
        frame.setLayout(null);
        frame.setVisible(true);

        JButton btnRefresh = new JButton("Actualiser");
        btnRefresh.setBounds(350, 400, 100, 23);
        frame.getContentPane().add(btnRefresh);
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                initialize();
            }
        });


        JButton btnSignOut = new JButton("Sign out");
        btnSignOut.setBackground(Color.BLUE);
        btnSignOut.setForeground(Color.MAGENTA);
        btnSignOut.setBounds(700, 400, 80, 23);
        frame.getContentPane().add(btnSignOut);

        btnSignOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                     boolean result = FirstInterfaceEtudiant.etudiant_api.signOut(id);
                     if (result){
                         frame.setVisible(false);
                         LogInInterface.showWindow();
                     }
                     else {
                         JOptionPane.showMessageDialog(null, "Il y a une erreur");
                     }
            }
        });





         //Envoyer un message public
        JButton btnSend = new JButton("Envoyer");
        btnSend.setBounds(250, 400, 80, 23);
        frame.getContentPane().add(btnSend);
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = txtMessage.getText().toString();
                int result = FirstInterfaceEtudiant.etudiant_api.sendMessage(id , -1, message);
                switch (result){
                    case 1 :
                        System.out.println("Le message a été envoyer");
                        txtMessage.setText(null);
                        break;
                    case 0 :
                        JOptionPane.showMessageDialog(null , "Le message a été envoyé au admin pour l'approuver");
                        txtMessage.setText(null);
                        break;
                    case -1 :
                        JOptionPane.showMessageDialog(null , "Il y a une erreur");
                        txtMessage.setText(null);
                        break;
                    default:
                        break;
                }
            }
        });

        frame.setSize(800,800);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.show();

}

    private String getMessagesPublic() {
       return FirstInterfaceEtudiant.etudiant_api.getMessagesPublic();
    }


}
