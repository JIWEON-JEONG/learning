package STUDY.TIL.java.thread;

public class RamenCook implements Runnable{

    private int ramenCount; //몇개의 라면을 끓일지
    public static String[] burnersState = {"_","_","_","_"};

    public RamenCook(int ramenCount) {
        this.ramenCount = ramenCount;
    }

    @Override
    public void run() {
        while (ramenCount > 0) {
            synchronized (this) {
                ramenCount--;
                System.out.println("thread name , ramenCount = " + ramenCount + " , " + Thread.currentThread().getName());
            }


            for (int i = 0; i < burnersState.length; i++) {
                if (!burnersState[i].equals("_")) continue;

                synchronized (this) {
                    burnersState[i] = Thread.currentThread().getName();
                    System.out.println(burnersState[i] + " burnner ON ");
                    display();
                }

                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                synchronized (this){
                    burnersState[i] = "_";
                    System.out.println(burnersState[i] + " burnner OFF ");
                    display();
                }
                break;
            }
            try {
                Thread.sleep(Math.round(1000 * Math.random()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void display(){
            String stringToPrint = "                                                ";
            for(int i = 0; i < burnersState.length; i++)
            {
                stringToPrint += (" " + burnersState[i]);
            }
            System.out.println(stringToPrint);
    }
}
