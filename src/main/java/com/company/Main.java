package com.company;

public class Main {

    public void print(){
        System.out.println("Main");
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

    //    public static void main(String[] args) {
//
//    }
}
