package org.example.algorithm;

import java.util.HashMap;
import java.util.Map;

public class HashAlgo {
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

    /**
     * nums/2 의 수만큼 숫자가 존재한다.
     * 숫자는 중복될 수 있고, 2가지 쌍으로 만들 수 있는 최대 가짓 수.
     */
    public int solution(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer i : nums) {
           map.put(i, map.getOrDefault(i, 0) + 1);
        }
        int max = nums.length / 2;
        int choices = map.size();
        return Math.min(max, choices);
    }

    public static void main(String[] args) {
        HashAlgo hashAlgo = new HashAlgo();
        int solution = hashAlgo.solution(new int[]{3, 1, 2, 3});
        System.out.println(solution);
    }
}
