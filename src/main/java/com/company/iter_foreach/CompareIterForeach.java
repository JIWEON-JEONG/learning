package com.company.iter_foreach;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class CompareIterForeach {

//    '강화된 for 반복문(Enhanced for loop)'라고도 불리는 For-each 반복문은 자바 1.8 버전에서 추가됐으며 for/while,do-while 반복문과 비슷하게 동작하는 반복문이다.
//    다른 반복문과 다른점은 For-each 반복문은 이터레이터의 단점을 극복하기 위해 나왔다.
//    이터레이터를 사용하려면 매번 Iterator 타입의 변수 생성, 초기화해야 한다. 그리고 반복문을 통해 이터레이터의 내부에 있는 커서를 옮겨서 컬렉션 아이템을 탐색할 수 있다.
//    Iterator타입 객체를 사용하여 아이템을 탐색할 경우 기본 코드(boilerplate code)가 많아 진다.

//    차이점 1. For-each 반복문은 처음부터 끝까지 모두 탐색한다.  // if 문으로 예외 처리 하지 않는 이상.


    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("정지원");
        list.add("김도현");


//        int[][] array = new int[3][3];
//        for (int i = 0; i < 3; i++) {
//            array[i][i] = i + 1;
//        }
//
//        Iterator<int[]> iter = Arrays.stream(array).iterator();
//        while (iter.hasNext()) {
//            System.out.println(iter.next()[0]);
//        }
//
//
//
//        for (int[] ints : array) {
//            System.out.println(ints[0]);
//        }

        for (String s : list) {
            if(s.equals("정지원")){
                list.remove(s);
            }
        }

        for (String s : list) {
            System.out.println(s);
        }
    }


}
