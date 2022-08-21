package STUDY.TIL.java.thread;

public class Ramen {
    public static void main(String[] args) {
        try{
            RamenCook ramenCook = new RamenCook(10);
            new Thread(ramenCook,"A").start();
            new Thread(ramenCook,"B").start();
            new Thread(ramenCook,"C").start();
            new Thread(ramenCook,"D").start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}





