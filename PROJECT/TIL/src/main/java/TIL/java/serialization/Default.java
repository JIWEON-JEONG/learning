package TIL.java.serialization;

import java.io.*;
import java.util.Date;
import java.util.Calendar;

/**
 * 어디다 저장??? 파일 , 메모리,  DB 여러 방법이 있다.
 *
 * Role 1 : The object to be persisted 는 Serializable 을 구현하거나 구현된 객체를 상속받아야 한다.
 *
 */
public class Default implements Serializable {

    private Date time;

    public Default()
    {
        time = Calendar.getInstance().getTime();
    }

    public Date getTime()
    {
        return time;
    }

    //FileInputStream : 파일의 내용을 읽음
    //FileOutputStream : 파일의 내용을 작성함
    public static void main(String[] args) {
        String filename = "time.ser";
        if (args.length > 0) {
            filename = args[0];
        }
        Default time = new Default();
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(filename);
            out = new ObjectOutputStream(fos);
            //실제 직렬화 시작.
            out.writeObject(time);
            out.close();
        }catch(IOException e){
            e.printStackTrace();
        }

//----------------------------------------------

        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream(filename);
            in = new ObjectInputStream(fis);
            //실제 직렬화 시작.
            time = (Default) in.readObject();
            in.close();
        }catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}

