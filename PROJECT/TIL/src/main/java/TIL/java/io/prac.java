package TIL.java.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class prac {
    public static void main(String[] args) {
        String value = "1234abcd";

        byte[] readBytes = value.getBytes(StandardCharsets.UTF_8);

        InputStream in = new ByteArrayInputStream(readBytes);

        byte[] writeBytes = new byte[1024];

        System.out.println(new String(writeBytes));


    }
}
