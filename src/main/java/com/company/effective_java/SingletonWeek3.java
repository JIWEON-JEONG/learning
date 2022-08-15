package com.company.effective_java;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

/**
 * 싱글톤 : 오직 한 인스턴스만 만들 수 있는 클래스
 * (보통 함수 같은 Stateless 객체 , 본질적으로 유일한 시스템 컴포넌트)
 * 싱글톤을 왜 만들까? - 다뤄보자. 활용 예
 *
 * 단점 : 클라이언트 코드를 테스트 하는게 어렵다.
 * (싱글톤이 인터페이스를 구현해서 만든 싱글턴이 아니라면, 싱글턴 인스턴스를 mock 구현으로 대체 할 수 없기 때문에) -> 잘 이해 X
 *
 * 싱글톤으로 만드는 3가지 방법
 * 1. final 필드를 이용한 방법 - static 팩토리 메서드를 이용하는 방법보다 명확하고 간단하다.
 * 2. static 팩토리 메서드를 이용한 방법
 * 3. enum class 로 관리 (가장 이상적)
 *
 *  * 현실 - spring 비유
 *  * -> Spring 에서 사용되는 빈들은 모두 singleton 으로 관리된다. (ApplicationContext 안에서 관리되는 bean 들만!)
 *  * @Scope("prototype") 으로 싱글턴이 아닌 객체로 관리도 가능.
 *
 *  객체 직렬화 api 의 비밀 : 다음주 토의 해보는 것도 좋을거같다.
 *  직렬화, 역직렬화 -> 네트워크 통신 할때, 캐시, 데이터 주고 받을때, 저장할때 (데이터를 옮길수 있는 상태로 만드는것 - 직렬화)
 *  자바의 Serialization 은 무엇일까 에 대해서 .. 링크 참조
 */
public class SingletonWeek3 {

    /**
     * static 이기 때문에 이 클래스가 처음 로드 될때 , 딱 한번만 만들어진다. (초기화 된다)
     */
    public static final SingletonWeek3 instance = new SingletonWeek3();

//    static int count = 0;
    static int count;
//    int count;
//    int count = 0;

    private SingletonWeek3() {
        System.out.println(count);
//        count++;
//        if (count != 1) {
//            throw new IllegalStateException("this object should be singleton");
//        }
    }
}

/**
 * 장점
 * 1. 싱글턴이 아니게 바꾸어도 외부에서 코드 변경 하지 않아도 된다. (유연성)
 * 2. 원한다면 정적 팩터리를 제네릭 싱글턴 팩터리로 만들수 있다.
 * 3. 정적 팩터리의 메서드 참조를 공급자 (supplier) 로 사용 할 수 있다.
 *
 * 이러한 장점들이 필요하지 않다면 public 필드 방식이 좋다.
 *
 */
class SingletonByStaticFactory {
    private static final SingletonByStaticFactory instance = new SingletonByStaticFactory();

    private SingletonByStaticFactory() {
    }

    /**
     * client 코드를 변경하지 않아도 된다. 유연성 good.
     * @return
     */
    public static SingletonByStaticFactory getInstance() {
//        return new SingletonByStaticFactory();
        return instance;
    }
}

/**
 * 상속 불가 , 구현 가능.
 */
enum SingletonByEnum {
    INSTANCE;

    String name = "";
    public String getName() {
        return "Jiweon";
    }
}

class SingletonTest {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//        SingletonWeek3 singleton = new SingletonWeek3();
        SingletonWeek3 instance1 = SingletonWeek3.instance;

        SingletonByStaticFactory instance2 = SingletonByStaticFactory.getInstance();

        SingletonByEnum instance3 = SingletonByEnum.INSTANCE;
        String name = instance3.getName();

        /**
         * supplier 에 대해서도 알아봐야겠다. -> 스터디 토의.
         */
        Supplier<SingletonByStaticFactory> supplier = SingletonByStaticFactory::getInstance;

        /**
         * reflection 으로 private 생성자 호출하는 방법.
         * reflection 에 대해 공부해보는 것도 좋겠다 -> 스터디 때 토의.
         */
//        Constructor<SingletonWeek3> constructor = SingletonWeek3.class.getConstructor(SingletonWeek3.class);
//        constructor.setAccessible(true);
//        SingletonWeek3 singletonWeek3 = constructor.newInstance(SingletonWeek3.instance);


    }
}