package STUDY.TIL.java.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class IteratorDemo {

    //Iterator 는 인터페이스. collection Interface를 구현하고 있는 모든 클래스는  iterator method를 통해 사용할 수 있다.
    public void test() {
        Collection<Integer> a = new HashSet<Integer>();
        ArrayList<String> b = new ArrayList<String>();
        a.add(1);
        a.add(2);
        a.add(3);
        b.add("one");
        b.add("two");
        b.add("three");
        Iterator iter = a.iterator();
        //hasNext() -> iter라는 컨테이너 안에 값이 있는지 boolean 으로 반환
        while(iter.hasNext()){
            //next() -> iter 컨테이너의 값을 리턴하고 그 값을 삭제한다. [원본 데이터는 살아있다]
            System.out.println(iter.next());
        }

        Iterator iter2 = b.iterator();
        while(iter2.hasNext()){
            System.out.println(iter2.next());
        }

    }
}
