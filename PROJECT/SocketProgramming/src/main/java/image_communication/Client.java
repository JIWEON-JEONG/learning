package image_communication;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 우선 파일에서 입력과 출력이라는 동작을 하려면 파일로 데이터를 전달하거나 파일로부터 전달 받는 길을 열어주어야합니다.
 * 그러한 길을 스트림이라고 하지요.
 *
 * FileReader : 파일의 끝은 int형의 -1입니다. 그래서 -1을 만날때까지 한문자 한문자 출력합니다.
 *
 * BufferedWriter와 BufferedReader는 버퍼의 사이즈를 지정할 수도 있습니다. 그렇지 않으면 Default 사이즈로 버퍼에 담습니다.
 * BufferedWriter의 newLine메소드는 개행을 말합니다.
 *
 * readLine은 String 형태의 문자열을 반환하는데, 만약 더이상 출력할 문자열이 없으면 null을 반환하죠.
 *
 *
 */
public class Client {

    Socket clientSocket;

    BufferedInputStream bufIn;

    BufferedOutputStream bufOut;

    FileOutputStream fileOut;

    public void serverConnection() throws Exception {
        InetAddress inetAddress = Inet4Address.getByName("127.0.0.1");
        clientSocket = new Socket(inetAddress, 7080);

        bufIn = new BufferedInputStream(clientSocket.getInputStream());

        System.out.println("Copy ...");

        fileOut = new FileOutputStream("./copy.png");
        bufOut = new BufferedOutputStream(fileOut);

        int data;
        while ((data = bufIn.read()) != -1) {
            bufOut.write(data);
        }
        System.out.println("Complete ...");
    }

    public void stop() throws Exception {
        bufOut.close();
        bufIn.close();
        clientSocket.close();
        fileOut.close();
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.serverConnection();
        client.stop();
    }
}
