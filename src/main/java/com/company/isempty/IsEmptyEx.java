package com.company.isempty;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class IsEmptyEx {

    public static void main(String[] args) {
        String a = new String();
        System.out.println(a.isEmpty());
        HashMap<String, String> dic = new HashMap<>();
        dic.put("baby", "아기");
        dic.put("love", "사랑");
        dic.put("apple", "사과");
        Set<String> keys = dic.keySet();
        Iterator<String> iter = keys.iterator();
        while (iter.hasNext()) {
        String key = iter.next();
        String value = dic.get(key);
        System.out.println("key : "+ key + ", value : " + value);
        }
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        while (true) {
        System.out.print("찾고 싶은 단어는? (종료 -> exit) ");
        String eng = scanner.next();
        if(eng.equals("exit")){
        System.out.println("단어 찾기 종료");
        break;
        }
        String kor = dic.get(eng);
        if(kor.isEmpty()){
            System.out.println("입력 값 " + kor);
            System.out.println(eng + " 는 없는 단어 입니다.");
        } else System.out.println(kor);
        }

    }
}
