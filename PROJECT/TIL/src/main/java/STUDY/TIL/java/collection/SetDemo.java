package STUDY.TIL.java.collection;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SetDemo {
    //Set 과 List 의 차이점 : 중복 값 처리 유무 , 순서 존재 유무 (index)
    public void test() {
        Set<Integer> a = new HashSet<Integer>();
        HashSet<Integer> b = new HashSet<Integer>();
        HashSet<Integer> c = new HashSet<Integer>();
        a.add(1);
        a.add(2);
        a.add(2);
        a.add(3);

        b.add(4);

        c.add(1);

        Iterator iter = a.iterator();
        while(iter.hasNext()){
            System.out.println(iter.next());
        }

        System.out.println(a.containsAll(b));   //b는 a의 부분집합인가
        System.out.println(a.contains(3));
        //a.addAll(b)  a 합집합 b
        //a.retainAll(b) a 교집합 b
        //a.removeAll(b) a 차집합 b
    }


}
