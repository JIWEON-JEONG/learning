package TIL.java.oop;

public class Barista implements Person,Chief{

    @Override
    public void make() {
        System.out.println("최상의 음료 제조중...");
        System.out.println("제조 완료");
    }

    @Override
    public void hiToCustomer() {
        System.out.println("안녕하세요 지원베이커리입니다. 최상의 음료를 제공하겠습니다.");
    }

}
