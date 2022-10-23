package TIL.java;

public class Main {

    public void print(){
        System.out.println("com.company.Main");
    }

    static {
        System.out.println("static block main");
    }

    {
        System.out.println("main instance block");
    }

    public Main() {
        System.out.println("main constructor");
    }
}
