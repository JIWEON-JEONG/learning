package STUDY.TIL.java.javacafe2week;

import java.util.*;
import java.util.stream.Collectors;

public class EX1 {
    public static void main(String[] args) {
        new ArrayList<>(10_000_000);
        //capacity 수동조작
    }

    public void ex1() {
        int[] arr = {9, 7, 1, 8, 2, 1, 23, 4, 11, 1, 8, 2, 6, 21, 18,};
        Set<Integer> set = new HashSet<>();
        for (int value : arr) {
            set.add(value);
        }

        for (Integer integer : set) {
            System.out.println(integer);
        }

        //정렬은 List 로 다시 변환 해주어야한다.

        List<Integer> numbers = Arrays.asList(9, 7, 1, 8, 2, 1, 23, 4, 11, 1, 8, 2, 6, 21, 18);
        Set<Integer> distinctedNumbers = new HashSet<>(numbers);
        System.out.println(distinctedNumbers);

        List<Integer> results = new ArrayList<>(distinctedNumbers);
        Collections.sort(results, Comparator.reverseOrder());

        System.out.println(results);

        //실무레벨에서는 많이 쓰임 -> 더 깔끔
        System.out.println(numbers.stream().distinct().sorted().collect(Collectors.toList()));
    }

    public void ex2(){
        List<Integer> numbers = Arrays.asList(7, 7, 1, 8, 2, 1, 1, 11, 1, 4, 2, 9, 7, 10);
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer integer : numbers) {

            if (!map.containsKey(integer)) {
                map.put(integer, 0);
            }

            Integer key = map.get(integer);
            map.put(integer, key + 1);
        }

        //stream 사용
//        numbers.stream().
    }


}
