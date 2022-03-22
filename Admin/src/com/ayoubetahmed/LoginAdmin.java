package com.ayoubetahmed;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginAdmin {

    private JFrame frame;
    private JTextField txtPseudo;
    private JPasswordField txtPassword;
    public static AdminAPI adminAPI = new AdminAPI();
    public static final String IP = "192.168.1.43";
    public static final int PORT = 4444;

    public  void showWindow() {
        adminAPI.connectToServer(IP , PORT);
        frame.setVisible(true);
    }





    public LoginAdmin()  {
        initialize();
    }

    public static void main(String[] args) {
        new LoginAdmin().showWindow();

    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 730, 489);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lbl = new JLabel("Espace d'Admin");
        lbl.setBounds(200, 40 ,400, 100);
        lbl.setFont(new Font("Calibri", Font.BOLD, 20));
        frame.getContentPane().add(lbl);

        //email
        JLabel lblEmail = new JLabel("Pseudo");
        lblEmail.setBounds(65, 155, 46, 14);
        frame.getContentPane().add(lblEmail);


        txtPseudo = new JTextField();
        txtPseudo.setBounds(128, 155, 247, 17);
        frame.getContentPane().add(txtPseudo);
        txtPseudo.setColumns(10);

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
        btnLogin.setBounds(65, 250, 120, 23);
        frame.getContentPane().add(btnLogin);


        JLabel lblMsg = new JLabel();
        lblMsg.setBounds(65, 300 ,400, 14);
        frame.getContentPane().add(lblMsg);



        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(txtPseudo.getText().isEmpty()||(txtPassword.getText().isEmpty())){
                    JOptionPane.showMessageDialog(null, "Data Missing");
                }else{
                    String pseudo = txtPseudo.getText().toString();
                    String password = txtPassword.getText().toString();
                    boolean correct = adminAPI.login(pseudo , password);
                      if (correct){
                          frame.setVisible(false);
                          new InterfaceTableAdmin().showWindow();
                      }
                      else{
                          JOptionPane.showMessageDialog(null, "Les inforamations sont incorrectes");
                      }
                }
            }
        });
    }

}




