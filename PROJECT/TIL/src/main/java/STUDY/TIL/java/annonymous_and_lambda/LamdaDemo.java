package STUDY.TIL.java.annonymous_and_lambda;

public class LamdaDemo {
    public static void main(String[] args) {
        //Object obj =(a, b) -> a > b ? a : b; //람다식. 익명객체

        /*
        Myfunction myfunction = new Myfunction() {
            @Override
            public int max(int a , int b) {
                return a > b ? a : b;
            }
        };
        */

        Myfunction myfunction = (a,b) -> a > b ? a : b;

        int value = myfunction.max(3, 5);
        System.out.println("value = " + value);
    }

    //@FunctionalInterface    //함수형 인터페이스는 단하나의 추상 메소드만 가질수 있다.
    interface Myfunction{
        //public abstract int max();
        int max(int a, int b);
    }
}
