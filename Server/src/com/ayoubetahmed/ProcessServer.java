package com.ayoubetahmed;

import java.io.*;
import java.net.Socket;

public class ProcessServer extends Thread{
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private ConnectionDB db = new ConnectionDB();

    public ProcessServer(Socket socket) {
        this.socket = socket;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //Servire chaque client
        //Les besoins de client
        //Admin -> Login - Activer un étudiant - Approuver un étudiant - voir les messages de chaque étudiant
        //Etudiant -> Sign up - Login - Envoyer un message privé et public
        while (!socket.isClosed()){
            try {
                String requestFromClient = this.in.readLine();
             //   System.out.println(requestFromClient);
                if (requestFromClient != null) {


                    String[] elementsOfRequest = requestFromClient.split(":");
                    switch (elementsOfRequest[0]) {
                        case "RequestLoginStudent":
                            String userName = elementsOfRequest[1];
                            String password = elementsOfRequest[2];
                            String responseLogin = loginStudent(userName, password);
                            //        System.out.println(responseLogin);
                            String[] elements = responseLogin.split(":");
                            int id = Integer.parseInt(elements[0]);
                            int isActivated = Integer.parseInt(elements[1]);
                            int isApproved = Integer.parseInt(elements[2]);
                            String pseudo = elements[3];
                            if (id != -1) {
                                //        int[] isActivatedAndIsApproved = {isActivated , isApproved};

//                                String activated ="";
//                                String approved = "";
//                                if (isActivatedAndIsApproved[0] == 0){
//                                    activated = "NotActivated";
//                                    approved = "NotApproved";
//                                }else if (isActivatedAndIsApproved[0] == 1){
//                                    activated = "Activated";
//                                    if (isActivatedAndIsApproved[1] == 0){
//                                        approved = "NotApproved";
//                                    }else if (isActivatedAndIsApproved[1] == 1){
//                                        approved = "Approved";
//                                    }
//                                }
                                out.println("" + id + ":" + isActivated + ":" + isApproved + ":" + pseudo);
                            } else {
                                out.println("-1:-1:-1:-1");
                            }
                            break;

                        case "RequestSignUp":
                            System.out.println("signup");
                            String nom = elementsOfRequest[1];
                            String prenom = elementsOfRequest[2];
                            pseudo = elementsOfRequest[3];
                            userName = elementsOfRequest[4];
                            password = elementsOfRequest[5];
                            int inserted = signUpStudent(nom, prenom, pseudo, userName, password);
                            //return 1 -> correcte
                            //return -1 -> changer le pseudo
                            //return -2 -> changer le username
                            out.println(""+inserted);
                            break;

                        case "RequestLoginAdmin":
                            pseudo = elementsOfRequest[1];
                            password = elementsOfRequest[2];
                            boolean found = loginAdmin(pseudo, password);
                            if (found) {
                                out.println("EventLoggedInAdmin");
                            } else {
                                out.println("EventNotLoggedInAdmin");
                            }
                            break;
                        case "RequestActive":
                            int id_student = Integer.parseInt(elementsOfRequest[1]);
                            boolean result = activeStudent(id_student);
                            //  System.out.println(result);
                            if (result) {
                                out.println("StudentIsActivated:" + id_student);
                            } else {
                                out.println("StudentFailActivated:" + id_student);
                            }
                            break;
                        case "RequestApprove":
                            id_student = Integer.parseInt(elementsOfRequest[1]);
                            result = approveStudent(id_student);
                            if (result) {
                                out.println("StudentIsApproved:" + id_student);
                            } else {
                                out.println("StudentFailApproved:" + id_student);
                            }
                            break;

                        case "RequestDataOfTable":
                            String response = getNotActivatedOrApprovedStudent();
                            out.println(response);
                            break;
                        case "RequestSignOut":
                            id_student = Integer.parseInt(elementsOfRequest[1]);
                            result = signOutStudent(id_student);
                            if (result) {
                                out.println("EventSignOutSuccess");
                            } else {
                                out.println("EventNotSignOut");
                            }
                            break;
                        case "RequestConnectedStudents":
                            //  System.out.println(elementsOfRequest[1]);
                            id = Integer.parseInt(elementsOfRequest[1]);
                            response = getConnectedStudents(id);
                            out.println(response);
                            break;
                        case "RequestSendMessage":
                            OutThread outThread = new OutThread(requestFromClient, socket);
                            outThread.start();
                            break;

                        case "RequestIdByPseudo":
                            String[] element = requestFromClient.split(":");
                            id = db.findIdByPseudo(element[1]);
                            out.println(""+id);
                            break;
                        case "RequestMessagesPublic":
                            String responseToClient = getPublicMessages();
                            System.out.println(responseToClient);
                            out.println(responseToClient);
                            break;
                        case "RequestMessagesPrivate":
                            int id_sender = Integer.parseInt(elementsOfRequest[1]);
                            int id_recipient = Integer.parseInt(elementsOfRequest[2]);
                            responseToClient = getPrivateMesssages(id_sender , id_recipient);
                            System.out.println(responseToClient);
                            out.println(responseToClient);
                            break;
                        case "RequestGetMessagesOfASpecificStudent":
                            id = Integer.parseInt(elementsOfRequest[1]);
                            responseToClient = getAllMessagesOfASpecificStudent(id);
                            out.println(responseToClient);
                            break;
                        default:
                            break;
                    }
                }
            } catch (IOException e) {
          //    e.printStackTrace();
            }
        }
    }

    private String getAllMessagesOfASpecificStudent(int id) {
        return db.getAllMessagesOfASpecificStudent(id);
    }

    //get all the messages btw id_sender and id_recipient
    private String getPrivateMesssages(int id_sender , int id_recipient) {
        return db.getPrivateMessages(id_sender,id_recipient);
    }

    //get all the public messages
    private String getPublicMessages() {
       return db.getPublicMessages();
    }

    //get the connected students
    private String getConnectedStudents(int id) {
        return db.getConnectedStudents(id);
    }

    //changer le champ isconnected dans le tableau de student de 1 à 0
    private boolean signOutStudent(int id_student) {
        return db.singOutStudent(id_student);
    }


    //get all the students not activated or not approved
    private String getNotActivatedOrApprovedStudent() {
        return db.getNotActivatedOrApprovedStudent();
    }

    private boolean loginAdmin(String pseudo, String password) {
        return db.findByLoginAdmin(pseudo , password);
    }

    //changer le champ de isapproved  d'étudiant de 0 à 1
    private boolean approveStudent(int id_student) {
        return db.approveStudent(id_student);
    }

    //changer le champ de isapproved  d'étudiant de 0 à 1
    private boolean activeStudent(int id_student) {
        return db.activeStudent(id_student);
    }

    //insérer un nouveau étudiant dans la base de données
    private int signUpStudent(String nom, String prenom, String pseudo, String userName, String password) {
             return db.findBySignUpStudent(nom , prenom , pseudo , userName , password);
    }

    //changer le champ isconnected dans le tableau de student de 0 à 1
    private String loginStudent(String userName , String password) {
        //id:isactivated:isapproved:pseudo
         String response = db.findByLoginStudent(userName , password);
        return response;
    }



    //Creation de thread pour inserer les messages dans la base de données de client
    class OutThread extends Thread{
        private PrintWriter outTh;
        private String requestFromClient ;
        public OutThread(String requestFromClient , Socket socket) {
            try {
                this.outTh = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                this.requestFromClient = requestFromClient;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
                String[] elements = this.requestFromClient.split(":");
                if (elements[0].equals("RequestSendMessage")){
                    int id_sender = Integer.parseInt(elements[1]);
                    int id_recipient = Integer.parseInt(elements[2]);
                    String message = elements[3];
                    int result = sendMessage(id_sender,id_recipient ,message);
                    outTh.println(""+result);
                }
        }

        private int sendMessage(int id_sender,int id_recipient , String message) {
              return db.sendMessage(id_sender ,id_recipient , message);
        }
    }
}




















