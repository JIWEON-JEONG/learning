import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class IOTest implements in{

    public static void main(String[] args) throws IOException {
        //기본 32 바이트. 음수면 예외 발생.
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        stream.write(464343353);
        stream.write(3123142);
        // write 메소드 stream 오버헤드가 있기 때문에, byte 배열로 만들어 한번에 넣기.
        byte[] code = new byte[50];
        //이거는 ByteArrayOutputStream 를 쓴게 아니라 부모 꺼 사용 한거. (write 반복 돌린거랑 같음)
        stream.write(code);
        //
        stream.write(code,0,code.length);
        System.out.println(stream.toString());
    }
    @Override
    public void print() {
        in.super.print();
    }
}

interface in{
    default void print() {
        System.out.println("111");
    }

    default void print2() {

    }
}