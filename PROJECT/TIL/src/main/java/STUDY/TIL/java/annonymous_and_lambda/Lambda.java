package STUDY.TIL.java.annonymous_and_lambda;


import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * 익명클래스의 한가지 문제점은 구현된 익명 객체가 simple 할 경우 ,
 * such as an interface that contains only one method, then the syntax of anonymous classes may seem unwieldy(복잡,불분명) and unclear.
 * In these cases, you're usually trying to pass functionality as an argument to another method,
 * 예를 들어 what action should be taken when someone clicks a button.
 * Lambda expressions enable you to do this, to treat functionality as method argument, or code as data.
 *
 * 자바는 OOP 이지만 함수형 기능도 지원한다. - 이부분은 공부하면서 뭔가 느낌.(재사용성에 관하여.)
 * <p>
 * 람다식은 익명함수(메서드라고 표현하지 않음..-> 클래스 내부에 포함되지 않아도 되기 때문)이다.
 * 자바에서의 람다식은 익명 메서드가 아닌 익명 객체이다. (자바에서는 메서드만 존재할 수 없기 때문에) - 말장난. : 이말은 틀린말인가?
 * <p>
 * 규칙
 * 1. 반환 값이 있는 경우, 식이나 값만 적고 return 문 생략 가능. ; 생략.
 * 2. 매개변수의 타입이 추론 가능하면, 타입 생략 가능.
 * 3. 매개 변수 하나일 경우 괄호 생략 가능 (타입 적어야할 경우는 생략 불가능)
 * 4. 문장 한문장 일때는 괄호 생략 가능 ; 안붙임.
 * <p>
 * 람다식을 어디에 담아야 할까? -> 함수형인터페이스의 등장 배경.
 * <p>
 * 함수형 인터페이스 왜 추상 메서드 한개만 정의 해야해? 여러개 정의하면 안돼? -> 익명 클래스 와 람다의 차이점과 관련이 있나? : 스터디.
 * 내가 생각한 이유 : 함수 라는게 하나의 기능을 제공한다는 건데. 그래서 단일 기능만 제공한다 이런건가
 *
 * 자주 사용되는 함수형 인터페이스 자바에서 제공
 * Runnable(매개변수 X, 반환값 X)
 * Supplier(매개변수 X, 반환값 O)
 * Consumer(매개변수 O, 반환값 X)
 * Function(매개변수 O, 반환값 O)
 * Predicate(조건식, return Boolean)
 *
 * 동작을 파라미터로 전달 하기 위해선?
 * 1.
 */
public class Lambda implements Interface{

    @Override
    public void printMax(CompareFunction function, int a, int b) {
        int result = function.max(a, b);
        System.out.println(result);
    }

    public static void main(String[] args) {
        Lambda lambda = new Lambda();
        int a = 5;
        int b = 0;
        FunctionCustom anonymousFunction = new FunctionCustom() {
            public int calc(int a, int b) {
                return a + b;
            }
        };

        /**
         * 람다 타입은 인터페이스로만 받을 수 있다.
         */
//        Object object = (int val1, int val2) -> {
//            return val1 < val2 ? val1 : val2;
//        };

        FunctionCustom lambdaFunction = (val1, val2) -> val1 + val2;

        BiFunction<Integer, Integer, Integer> getAddResult = (val1, val2) -> val1 + val2;
        getAddResult.apply(1, 5);

        Predicate<String> isEmpty = (sentence) -> sentence.length() == 0;
        isEmpty.test("v");

        lambda.printMax(new CompareFunction() {
            @Override
            public int max(int a, int b) {
                return Math.max(a, b);
            }
        },a,b);

        lambda.printMax((val1, val2) -> val1 > val2 ? val1 : val2, 5, 0);
    }

}

interface Interface {
    void printMax(CompareFunction function, int a, int b);
}

@FunctionalInterface
interface FunctionCustom {
    public abstract int calc(int a, int b);
//    int send();
}

@FunctionalInterface
interface CompareFunction {
    public abstract int max(int a, int b);
//    int send();
}

/**
 * 예시 라이브 코딩 : Collection.sort(list , new Comparator<String>(){ public int compare ... }
 */
class LiveExample {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 10; i > 0; i--) {
            list.add(i);
        }
        list.add(7);
        list.sort((Integer o1, Integer o2) -> {
            if (o1 > o2){
                return 1;
            } else if (o2 > o1) {
                return -1;
            }
            return 0;
        });
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }
}
