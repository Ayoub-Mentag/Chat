package com.ayoubetahmed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 4444;

    public void connectDB(){
          new ConnectionDB();
    }

    public static void main(String[] args) throws IOException {
        //Connection au Base de données
        new Server().connectDB();

        //Creation de port d'écoute
        ServerSocket serverSocket = new ServerSocket(PORT);

        //Creation de Process (thread) pour chaque client

        while (true){
               Socket socket = serverSocket.accept();
               System.out.println("A new client has been connected");
               ProcessServer processServer = new ProcessServer(socket);
               processServer.start();
        }


    }
}
