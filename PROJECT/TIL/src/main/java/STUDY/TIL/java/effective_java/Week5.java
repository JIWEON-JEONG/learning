package STUDY.TIL.java.effective_java;

import java.util.List;
import java.util.Objects;

/**
 * 아이템 5 : 리소스를 엮을 때는 의존성 주입을 선호하라.
 * 먼저 의존성 주입이란? --> 스터디 정리.
 * feat : SpellChecker.
 *
 * 사전을 하나만 사용하는것이 아닌, 각 언어의 맞춤법 검사기는 사전마다 다 다르다.
 * 어떤클래스가 사용하는 리소스에 따라 행동을 달리 해야하는 경우 static utility 또는 Singleton 은 부적절하다.
 *
 * 적절한 구현.
 * - 유연해진다.
 * - 의존성 주입은 빌더에도 적용가능하다.
 * - Test 할때에도 단위테스트 가능. Lexicon 을 Test 용으로 갈아끼어서 하는 방법으로.
 *
 * 주의 할 점
 * - 의존성이 많은 경우에는 코드가 장황해질 수 있다. -> 프레임워크를 사용하여 해결 할 수 있다.
 *
 * 결론 : 의존하는 리소스에 따라 행동을 달리하는 클래스를 만들때, 생성자나 팩토리로 전달하는 의존성 주입을 사용하자.
 *
 */
public class Week5 {

    private final Lexicon dictionary;

    public Week5(Lexicon dictionary) {
        this.dictionary = Objects.requireNonNull(dictionary);
    }

    public boolean isValid(String word) {
        throw new UnsupportedOperationException();
    }

    public List<String> suggestions(String type) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        Lexicon testLexicon = new TestDictionary();
        Week5 checker = new Week5(testLexicon);
        Week5_2.INSTANCE.isValid("hello");
    }
}

/**
 * singleton 방식 사용
 * 단점
 * private final Lexicon dictionary = new KoreanDictionary();
 * 만약 다른 사전을 써야할 경우 유연하지가 않다.
 * 이 라인 전체 수정. -> 원래 수정해야하지 않나?
 */
class Week5_2 {
    private final Lexicon dictionary = new KoreanDictionary();

    private Week5_2() {

    }

    public static final Week5_2 INSTANCE = new Week5_2();

    public boolean isValid(String word) {
        throw new UnsupportedOperationException();
    }

    public List<String> suggestions(String type) {
        throw new UnsupportedOperationException();
    }
}

/**
 * 1. static 한 유틸 클래스
 * -> 왜 resource 가 static 해야할까?
 * 단점
 * 1. new KoreanDictionary() 고정. -> 이게 단점인가?
 * 2. Test 할때 Week5 뿐만아닌 dictionary 도 같이 테스트 된다.
 */
class Week5_3 {
    //resource.
    private static final Lexicon dictionary = new KoreanDictionary();

    //non-instantiable
    private Week5_3(){}

    public static boolean isValid(String word) {
        throw new UnsupportedOperationException();
    }

    public static List<String> suggestions(String type) {
        throw new UnsupportedOperationException();
    }

}

interface Lexicon {}

class KoreanDictionary implements Lexicon {
    public static void print() {
        System.out.println("111111");
    }

    public KoreanDictionary() {
        System.out.println("111132");
    }
}

class TestDictionary implements Lexicon {

}