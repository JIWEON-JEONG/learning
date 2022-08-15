package com.company.oop;

public class Baker implements Person,Chief{
    @Override
    public void hiToCustomer() {
        System.out.println("안녕하세요 지원베이커리입니다. 최상의 빵을 제공하겠습니다.");
    }

    @Override
    public void make() {
        System.out.println("최상의 빵 요리중 ...");
        System.out.println("요리 완료");
    }
}
