package com.company.effective_java;

/**
 * 생성자 대신 static 팩토리 메소드를 고려해 볼 것
 * 장점
 * 1. 이름을 가질 수 있다. (생성자는 시그니처의 제약이 있다.)
 * 2. 반드시 새로운 객체를 만들지 않아도 된다.
 * 3. 리턴하는 객체의 클래스가 입력 매개변수에 따라 매번 다를 수 있다. (유연성)
 * 4. 리턴하는 객체의 클래스가 public static 팩토리 메서드를 작성할 시점에 반드시 존재하지 않아도 된다.
 * 5. 리턴타입의 하위 타입 인스턴스를 만들 수도 있다.
 *
 * 단점
 * 1. 프로그래머가 static 팩토리 메서드를 찾는게 어렵다.
 * -> JAVA doc 문서 : 생성자는 상단에 모아서 보여주지만,
 *  static 팩토리 메서드는 제공 X. 클래스나 인터페이스 상단에 내용 정리 해놓는거 권장.
 */
public class Week1 {

    String name;

    String address;


    private static final Week1 GoodNight = new Week1();

    public Week1() {
    }

    public Week1(String name) {
        this.name = name;
    }

    public static Week1 getWeek1() {
        // 객체를 생성하지 않아도 된다.
        return GoodNight;
    }

    //    불가능
//    public Week1(String address) {
//        this.name = address;
//    }

    public static Week1 withName(String name) {
        return new Week1(name);
    }

    public static Week1 withAddress(String address) {
        return new Week1(address);
    }

    //매개변수에 따라 다른 객체 리턴
    public static Week1 flexibleMethod(boolean b) {
        return b ? new Week1() : new Week1Child();
    }

    public static void main(String[] args) {
        Week1 week1 = new Week1("JIWEON");
        Week1 weekWithName = withName("JIWEON");
        Week1 weekWithAddress = withAddress("Seoul");
    }

    static class Week1Child extends Week1 {

    }
}


