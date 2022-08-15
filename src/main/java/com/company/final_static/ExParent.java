package com.company.final_static;

import com.company.create_object.CreateObject;

public class ExParent {

    public int iv = 3;
    //간단 초기화
    static int width = 200;

    static int count;

    //복잡한 초기화 수행
    static {
        count = 0;
        new CreateObject();
    }

    final void print() {
        System.out.println("instance method");
    }

    static void staticPrint() {
//        iv = 300;
        //count = 0
        System.out.println(count);
        width = 300;
        System.out.println("static method");
    }

    void extendsMethod() {
        System.out.println("extends method");
    }

    public static void doSomething() {
        // 청소를 한다.
        // 여자친구와 데이트를 한다.
        // 강아지 산책 시킨다.
        // 밥을 먹는다.
        // 잔다.
        doSomethingEveryday();
    }

    public void doSomethingTomo() {
        // 코딩을 한다.
        // 강아지 산책 시킨다.
        // 밥을 먹는다.
        // 도현이와 구미를 한다.
        // 잔다.
        doSomethingEveryday();
    }

    private static void doSomethingEveryday() {
        // 강아지 산책 시킨다.
        // 밥을 먹는다.
        // 잔다.
    }
}

final class ExChild extends ExParent {

    @Override
    void extendsMethod() {
        super.extendsMethod();
    }

    int iv = super.iv;

    int width = super.width;

    void extendsMethod2() {
        super.iv = 3;
        print();
        staticPrint();
    }

//    void print() {
//        System.out.println("extends test");
//    }
}

//class ExGrandChild extends ExChild {
//
//}
