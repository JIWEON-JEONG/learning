import java.io.*;


public class FileTest {
    public static void main(String[] args) throws Exception {
        //input 다 읽으면 -1 뱉는다.
        FileInputStream input = new FileInputStream("/Users/BestFriend/Desktop/PROJECT/SocketProgramming/src/test/resources/devops.png");
        FileOutputStream output = new FileOutputStream("/Users/BestFriend/Desktop/PROJECT/SocketProgramming/src/test/resources/copy.png");

        int data = 0;
        while ((data = input.read()) != -1) {
            output.write(data);
        }
    }
}

