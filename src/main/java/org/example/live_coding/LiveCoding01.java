package org.example.live_coding;

import java.util.*;

/**
 * 조건들이 존재 하고. 우선순위를 매겨서, 점수에 따라서 결과를 도출해야한다.
 * 조건들에 대해서 점수를 준다.
 * 점수를 비교하여 결과를 도출한다.
 */
public class LiveCoding01 {
    public String solution(String input) {
        // input 변환 & 검증
        if (input == null) {
            throw new IllegalArgumentException("입력 문자열은 null일 수 없습니다.");
        }
        List<List<Integer>> inputs = new ArrayList<>();

        String[] parts = input.split(",");
        for (String part : parts) {
            //하나 이상의 공백을 구분자로 사용.
            String[] numberStrings = part.trim().split(" +");
            List<Integer> currentNums = new ArrayList<>();
            for (String ns : numberStrings) {
                currentNums.add(Integer.parseInt(ns));
            }
            inputs.add(currentNums);
        }

        int firstScore = calculateScore(inputs.get(0));
        int secondScore = calculateScore(inputs.get(1));

        System.out.println(firstScore);
        System.out.println(secondScore);

        if(firstScore > secondScore){
            return "first";
        } else if (firstScore < secondScore) {
            return "second";
        }
        return "draw";
    }

    public int calculateScore(List<Integer> nums) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (Integer num : nums) {
            counts.put(num, counts.getOrDefault(num, 0) + 1);
        }
        if(condition01(counts)){
            return 5;
        }else if(condition02(counts)){
            return 4;
        }else if(condition03(counts)){
            return 3;
        }else if(condition04(counts)){
            return 2;
        }else if(condition05(counts)){
            return 1;
        }
        return 0;
    }

    // 중복제거 후, 인덱스 비교.
    private boolean condition01(Map<Integer, Integer> nums) {
        return nums.keySet().stream().toList().size() == 1;
    }

    // 중복제거 후, 인덱스 비교.
    private boolean condition02(Map<Integer, Integer> nums) {
        List<Integer> keys = nums.keySet().stream().toList();
        if (keys.size() != 5) {
            return false;
        }
        Collections.sort(keys);
        for (int i = keys.get(0); i < keys.get(0) + 5; i++) {
            Integer orDefault = nums.getOrDefault(i, 0);
            if (orDefault == 0) {
                return false;
            }
        }
        return true;
    }

    private boolean condition03(Map<Integer, Integer> nums) {
        Optional<Integer> result = nums.values().stream().filter(count -> count == 4).findAny();
        return result.isPresent();
    }

    private boolean condition04(Map<Integer, Integer> nums) {
        Optional<Integer> result01 = nums.values().stream().filter(count -> count == 3).findAny();
        Optional<Integer> result02 = nums.values().stream().filter(count -> count == 2).findAny();
        return result01.isPresent() && result02.isPresent();
    }

    private boolean condition05(Map<Integer, Integer> nums) {
        long resultCount = nums.values().stream().filter(count -> count == 2).count();
        return resultCount == 2;
    }

    public static void main(String[] args) {
        LiveCoding01 liveCoding01 = new LiveCoding01();
        System.out.println(liveCoding01.solution("1,2,3,4,5,6,7,8,9,10"));
    }
}
