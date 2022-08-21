package STUDY.TIL.java.effective_java;

import java.util.regex.Pattern;

/**
 * item 6 : 불필요한 객체를 만들지 말자!
 *
 * 방법
 * 1. static factory method 사용하는 방법
 * ex) Boolean.valueOf() 또는 String 같은것은 내부적으로 공유함.
 *
 * 무거운 객체 즉 만드는데 메모리나 시간이 오래 걸리는 객체를 반복적으로 만들어야 한다면,
 * 캐시해두고 재사용할 수 있는지 고려하는 것이 좋다. -> ttbkk place api 예시.
 *
 * 예시 pattern 과 spring 에서 제공하는 @pattern 은 같을까? 같이 확인해보자.
 *
 * 2. 어댑터 방식
 * - map 을 통해 keySet 을 불러오면, 여러번 불러와도 같은 객체이다.
 * - keySet 을 삭제하게 되면, keySet 객체들 모두 영향을 받고 map 또한 영향을 받는다.
 *
 * 3. 오토박싱 : primitive 타입과 박스(reference) 타입을 섞어 쓸 수 있게 해주고 박싱과 언박싱을 자동으로 해준다.
 * - 불필요한 오토박싱을 피하려면 박스 타입 보다는 프리미티브 타입을 사용해야 한다.
 *
 * 결론 : 이번 아이템으로 인해 객체를 만드는 것은 비싸며 가급적이면 피해야 한다는 오해를 해서는 안된다.
 * 특히 방어적인 복사(Depensive copying)를 해야 하는 경우에도 객체를 재사용하면 심각한 버그와 보안성에 문제가 생긴다. 객체를 생성하면 그저 스타일과 성능에 영향을 줄 뿐인데...
 */
public class Week6 {

    /**
     * String.matches는 내부적으로 Pattern 객체를 만들어 쓰는데 그 객체를 만들려면 정규 표현식으로 유한 상태 기계로 컴파일 하는 과정이 필요하다.
     * <단점>
     * 즉 비싼 객체다.
     */
    static boolean isRomanNumeral(String s) {
        return s.matches("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
    }

    /**
     * 성능을 개선하기 위해 Pattern 객체를 만들어 재사용.
     * <단점>
     * isRomanNumeral 메소드가 호출되지 않을 경우, static 변수를 필요없이 만든셈이 된다.
     */
    static class RomanNumber {

        private static final Pattern ROMAN = Pattern.compile("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");

        static boolean isRomanNumeral(String s) {
            return ROMAN.matcher(s).matches();
        }
    }

    /**
     * 어댑터 방식
     * <단점>
     *     혼란을 줄 수 있다. 의존성이 크기 때문에. 여러 군데에 영향을 미친다.
     * </단점>
     * @param args
     */
//    public static void main(String[] args) {
//        Map<String, Integer> menu = new HashMap<>();
//        menu.put("Burger", 8);
//        menu.put("Pizza", 9);
//
//        Set<String> names1 = menu.keySet();
//        Set<String> names2 = menu.keySet();
//
//        names1.remove("Burger");
//        System.out.println(names2.size()); // 1
//        System.out.println(menu.size()); // 1
//    }

    /**
     * 불필요한 오토박싱으로 인한 자원 낭비
     * @param value
     * @return
     */
    static int autoBoxingExample(Integer value) {
        int a = 10;
        int result = value + a;
        return result;
    }

    static class AutoBoxingExample {

        public static void main(String[] args) {
            long start = System.currentTimeMillis();
            Long sum = 0l;
            for (int i = 0 ; i <= Integer.MAX_VALUE ; i++) {
                sum += i;
            }
            System.out.println(sum);
            System.out.println(System.currentTimeMillis() - start);
        }
    }
}
