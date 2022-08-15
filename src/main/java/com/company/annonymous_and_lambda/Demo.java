package com.company.annonymous_and_lambda;

/**
 * lambda 표현식 : 메서드로 전달 할 수 있는 익명 함수를 단순화 한것.
 * 특징
 * 익명 : 이름이 없다.
 * 함수 : 특정 클래스에 종속되지 않는다.
 * 전달 : 람다 표현식을 인수로 전달 하거나 변수로 저장 할 수 있다.
 * 간결성 : 자질구레한 코드를 구현할 필요가 없다.
 *
 * 결론 : 람다표현식을 통해 코드를 간결하고 유연하게 할 수 있다.
 *
 * 람다는 세부분으로 이루어져있다.
 * - 파라미터 리스트 , 화살표 , 람다의 바디
 *
 * stream 이란?
 * 데이터 컬렉션 반복을 멋지게 처리하는 기능.
 *
 * 장점
 *
 *
 * 알아야할것 -> oracle 공식문서 참조
 * - 람다와 익명내부클래스의 차이점.
 * -
 *
 */
public class Demo {
    public void useLambda() {
        Runnable runnable = () -> System.out.println("lambda");
        runnable.run();
    }

}
