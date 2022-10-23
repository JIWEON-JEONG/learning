package TIL.java.collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class MoreInfo {
    public static void main(String[] args) {
        new MoreInfo();
    }

    public MoreInfo() {
        //Vector 의 동기화 처리를 대체 하기 위해 -> 속도 down 안전 up
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        Set<String> set = Collections.synchronizedSet(new HashSet<>());
        Map<String,String> map = Collections.synchronizedMap(new HashMap<>());
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

        map.put("a", "b");
        map.put("c", "d");

        // * 선언 방식의 다양성 * //

        String[] arr = new String[5];
        arr[0] = "1";
        arr[1] = "2";
        arr[2] = "3";

        // java 8 이하
        Arrays.asList("JAVA", "KOTLIN", "C++");
        List<Map<String, String>> maps = Arrays.asList(map);
        List<String> list1 = Arrays.asList(arr);
        System.out.println(maps);
        System.out.println(map);
        System.out.println(list1);

        // java 9 이상
        List.of("JAVA", "KOTLIN", "C++");
        List.of(arr);

        // stream 이용
        Stream.of("JAVA", "KOTLIN", "C++").collect(toList());
        Stream.of(arr).collect(toList());

        // 자바 8 이하
        new HashSet(Arrays.asList("JAVA", "KOTLIN", "C++"));

        // 자바 9 이상
        Set.of("JAVA", "KOTLIN", "C++");

        // stream 이용
        Stream.of("JAVA", "KOTLINT").collect(Collectors.toSet());


    }
}
