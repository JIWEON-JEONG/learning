package org.example.algorithm;

import java.util.*;
import java.util.stream.Collectors;

public class HashAlgo {
    //https://school.programmers.co.kr/learn/courses/30/lessons/42576
    public String solution01(String[] participant, String[] completion) {
        Map<String, String> map = new HashMap<>();
        Map<String, Integer> duplicates = new HashMap<>();
        for (String c : completion) {
            if (map.containsKey(c)) {
                duplicates.put(c, duplicates.getOrDefault(c, 0) + 1);
            }else {
                map.put(c, c);
            }
        }
        for (String p : participant) {
            if (duplicates.get(p) != null && duplicates.get(p) > 0) {
                duplicates.put(p, duplicates.get(p) - 1);
            } else if (map.containsKey(p)) {
                map.remove(p);
            } else {
                return p;
            }
        }
        return "";
    }

    //https://school.programmers.co.kr/learn/courses/30/lessons/1845?language=java
    public int solution02(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer i : nums) {
           map.put(i, map.getOrDefault(i, 0) + 1);
        }
        int max = nums.length / 2;
        int choices = map.size();
        return Math.min(max, choices);
    }

    //https://school.programmers.co.kr/learn/courses/30/lessons/42577
    //01. 문제 이해하기 : String 배열 중, 어떤 요소가 다른 요소의 접두어 일 경우 False 아닐 경우 True
    //02. 구현 방식 계획하기 :
    // - 각 요소별로 보인 사이즈보다 큰 애들 찾아서 비교 하는 방법.
    // - 특정 요소 기준으로, hashmap 이용해서, key 에 넣고 value 값 비교.
    //03. 코드 작성하기
    // - Sorting 후 인접 요소들끼리만 비교하기
    //04. 단위 테스트하기
    //05. 성능 검증하기
    // - O(NlogN)
    public boolean solution03(String[] phone_book) {
        Arrays.sort(phone_book);
        for (int i = 0; i < phone_book.length - 1; i++) {
            if (phone_book[i + 1].startsWith(phone_book[i])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        HashAlgo hashAlgo = new HashAlgo();
    }
}
