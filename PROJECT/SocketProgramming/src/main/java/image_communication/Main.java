package image_communication;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
    // TCP Server
    ServerSocket serverSocket;
    Socket clientSocket;

    BufferedInputStream bufIn;

    FileInputStream fileIn;

    BufferedOutputStream bufOut;


    public void startServer() throws Exception {
        fileIn = new FileInputStream("/Users/BestFriend/Desktop/PROJECT/SocketProgramming/ttbkk.png");
        bufIn = new BufferedInputStream(fileIn);

        serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(Inet4Address.getByName("127.0.0.1"), 7080));
        System.out.println("Listen...");

        clientSocket = serverSocket.accept();
        System.out.println("Accept...");

        bufOut = new BufferedOutputStream(clientSocket.getOutputStream());
        int data;
        while ((data = bufIn.read()) != -1) {
            bufOut.write(data);
        }
        System.out.println("Send Complete...");
    }

    public void stopServer() throws Exception {
        bufIn.close();
        fileIn.close();
        bufOut.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.startServer();
        main.stopServer();
    }

}

