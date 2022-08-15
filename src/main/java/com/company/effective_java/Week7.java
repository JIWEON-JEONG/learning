package com.company.effective_java;

import java.lang.ref.WeakReference;
import java.util.*;

/**
 * Item 7 불필요한 객체 레퍼런스를 정리하자.
 * <p>
 * 모든 상황에 객체를 null 로 정리를 할 필요는 없다.
 * 메모리를 직접 관리 할때, Stack 구현체 처럼 element 라는 배열(멤버 필드)을 관리 하는 경우,
 * GC 는 어떤 객체가 필요없는 객체인지 알수 없다. 이러한 경우 고려해보자.
 *
 * Weak Reference : 스터디 때 직접 정리해보자. 이것이 무엇이고 언제 쓰는 게 좋은지, 동작이 어떻게 되는지.
 */
public class Week7 {
    /**
     * Item 7에 대한 예는 캐시, 콜백(이벤트 리스트로 저장한뒤 하나씩 처리) 등 있다.
     */
    public void cacheDemo() {
        Object key1 = new Object();
        Object value1 = new Object();

        /**
         * 문제점 :  객체의 레퍼런스를 캐시에 넣어 놓고 캐시를 비우는 것을 잊기 쉽다.
         */
        Map<Object, Object> cache = new HashMap<>();
        cache.put(key1, value1);

        /**
         * 해결법 : WeakHashMap
         * 동작원리 : key 값을 Weak 레퍼런스로 한번 감싸서 저장된다.
         *
         * 원래는 참조되는 레퍼런스가 없을 경우에 GC 대상이 되지만, Weak Reference 는 ...
         * key1 레퍼런스가 쓸모가 없어지면, weakKey1 도 GC 대상이 된다.
         */
        WeakReference<Object> weakKey1 = new WeakReference<>(key1);
        Map<Object, Object> upgradeCache = new WeakHashMap<>();
        cache.put(weakKey1, value1);
    }

}

class Stack {

    private Object[] elements;

    private int index = 0;

    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
        this.ensureCapacity();
        this.elements[index++] = e;
    }

    public Object pop() {
        if (index == 0) {
            throw new EmptyStackException();
        }

        /**
         * 해당 위치에 있는 객체를 꺼내주고 그값을 null 로 설정하여, 다음 GC 발생 할때
         * 레퍼런스가 먼저 정리 되게 만든다.
         *
         * NullPointerException 이 발생 할 순 있지만, 잘못된 객체를 돌려주는 것 보단 낫다. (해당 부분은 예외처리 해주면 될거같음)
         *
         */
        Object element = this.elements[--index];
        this.elements[index] = null;
        return element;
//        return this.elements[--index]; // 메모리 leak 발생.
    }

    /**
     * Ensure space for at least one more element,
     * roughly doubling the capacity each time the array needs to grow.
     */
    private void ensureCapacity() {
        if (this.elements.length == index) {
            this.elements = Arrays.copyOf(elements, 2 * index + 1);
        }
    }
}