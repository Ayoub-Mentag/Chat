package com.ayoubetahmed;

import java.sql.*;

public class ConnectionDB {
    private Connection connection = null;
    private Statement statement = null;
    private static final String CONNECTION_STRING = "jdbc:postgresql://localhost:5432/netp_roject";
    private static final String USER_NAME =  "postgres";
    private static final String PASSWORD = "3115";

    public ConnectionDB() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING , USER_NAME , PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int findIdByPseudo(String  pseudo){
        String query = "select id from student where pseudo = " + "'" + pseudo +"';";
        System.out.println(pseudo);
        try {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()){
                int id = resultSet.getInt("id");
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String findByLoginStudent(String userName , String password){
        String query = "Select id , isactivated ,isapproved , pseudo from student where username = '"+userName+"' and password = '"+password+"';";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()){
                int id = resultSet.getInt("id");
                int isactivated = resultSet.getInt("isactivated");
                int isapproved = resultSet.getInt("isapproved");
                String pseudo = resultSet.getString("pseudo");
                //change isconnected from 0 to 1
                connectStudent(true , id);
                String response = id+":"+isactivated+":"+isapproved+":"+pseudo;
                return response;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
         return "-1:-1:-1:-1";
    }

    public boolean singOutStudent(int id){
        return connectStudent(false , id);
    }

    //change is connected to 1 or 0 if the user make a login
    private boolean connectStudent(boolean b, int id) {
        int isconnected = 0;
        if (b){
            isconnected = 1;
        }
         String query = "update student set isconnected = " + isconnected+" where id = "+id+";";
         try {
            statement.executeUpdate(query);
            return true;
         } catch (SQLException e) {
            e.printStackTrace();
         }
         return false;
    }

    public int findBySignUpStudent(String nom , String prenom , String pseudo, String userName, String password) {
        //return 1 -> correcte
        //return -1 -> changer le pseudo
        //return -2 -> changer le username
        String query1 = "Select id from student where pseudo = '"+pseudo+"';";
        String query2 = "Select id from student where username = '" +userName+"';";
        try {
            ResultSet resultSet = statement.executeQuery(query1);
            if (!resultSet.next()){
                resultSet = statement.executeQuery(query2);
                if (!resultSet.next()){
                    insertNewStudent(nom , prenom , pseudo , userName , password);
                    return 1;
                }
                //il faut changer l'email
                return -2;
            }
            //il faut changer le pseudo
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void insertNewStudent(String nom, String prenom, String pseudo, String userName, String password) {
       String query = "insert into student(nom , prenom , pseudo , username , password)"+
               " values('"+nom+"' , '"+prenom+"' , '"+pseudo+"' , '"+userName+"' , '"+password+"')";
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean activeStudent(int id_student) {
        String query = "update student set isactivated = 1 where id = "+ id_student+";";
        try {
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean approveStudent(int id_student) {
        String query = "update student set isapproved = 1 where id = "+ id_student+";";
        try {
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean findByLoginAdmin(String pseudo, String password) {
        String query = "select * from admin where pseudo = '" + pseudo +
                "' and password = '" + password + "' ; ";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getNotActivatedOrApprovedStudent() {
        String query = "select id , nom , prenom , isactivated , isapproved from student where isactivated = 0 or isapproved = 0;";
        try {
            String response = "";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String nom = resultSet.getString(2);
                String prenom = resultSet.getString(3);
                int isactivated = resultSet.getInt(4);
                int isapproved = resultSet.getInt(5);
                response += ""+ id+" "+nom + " "+prenom+" "+isactivated+" "+isapproved+":";
            }


            if (!response.equals("")){
                response = response.substring(0, response.length()-1);
                return response;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getConnectedStudents(int id) {
        String query = "select pseudo from student where isconnected = 1 and id <> "+id+";";
        String response = "";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                System.out.println(resultSet.getString("pseudo"));
                response += resultSet.getString("pseudo") + ":";
            }
            if (!response.equals("")){
                response = response.substring(0 , response.length()-1);
                return response;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int sendMessage(int id_sender,int id_recipient ,  String message) {
            String query = "insert into messages(id_sender ,id_recipient , body) values("+id_sender+", '"+id_recipient+"' , '"+message+"');";
        try {
            statement.executeUpdate(query);
            //is this sender is he approved or not
            String queryIsSent = "Select id from student where isapproved = "+0+" and id = " + id_sender+";";
            ResultSet resultSet = statement.executeQuery(queryIsSent);
            if (resultSet.next()){
                //this person is not approved
                return 0;
            }else {
                //this person is approved
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return -1;
    }

    public String getPublicMessages() {
       String query = "select student.pseudo , messages.body , messages.send_at from messages join student on messages.id_sender = student.id" +
               " and messages.id_recipient = -1 and student.isapproved <> 0;";
        try {
            String messages = "";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                String pseudo = resultSet.getString("pseudo");
                String body = resultSet.getString("body");
                String send_at = resultSet.getString("send_at");
                messages += pseudo+" -> : @all : " + body + " : " + send_at + "/";
            }

            return messages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getPrivateMessages(int id_sender , int id_recipient) {
        String query = "select student.pseudo , messages.body , messages.send_at from messages "+
                "join student on messages.id_sender = student.id " +
                "and messages.id_sender in ( " + id_sender+ ","+ id_recipient +") "
                +"and messages.id_recipient in ( " + id_sender+ ","+ id_recipient +") and student.isapproved = 1"
                +" order by send_at";
        String messages = "";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                String pseudo = resultSet.getString("pseudo");
                String body = resultSet.getString("body");
                String send_at = resultSet.getString("send_at");
                messages += pseudo+" : "+body + " : " + send_at + "/";
            }
            return messages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getAllMessagesOfASpecificStudent(int id_sender){
        System.out.println("messagesOf specific S test");
        String query = "select messages.body  , student.pseudo from messages join student on " +
                "messages.id_recipient = student.id and  id_sender = "+ id_sender +";";
        try {
            String messages = "";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                String body = resultSet.getString("body");
                String pseudo = resultSet.getString("pseudo");
                messages += body +"/"+pseudo+":";
            }
            System.out.println(messages);
                return messages;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

}
