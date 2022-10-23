package week1;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 첫째 줄에 로그에 기록된 출입 기록의 수 n이 주어진다.
 * (2 ≤ n ≤ 106) 다음 n개의 줄에는 출입 기록이 순서대로 주어지며, 각 사람의 이름이 주어지고 "enter"나 "leave"가 주어진다.
 * "enter"인 경우는 출근, "leave"인 경우는 퇴근이다.
 * 회사에는 동명이인이 없다.
 */
public class P6 {
    public static void main(String[] args) throws Exception {
        P6 p6 = new P6();
        Scanner scanner = new Scanner(System.in);
        Map<String, String> map = new HashMap<>();
        int num = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < num; i++) {
            String log = scanner.nextLine();
            String[] split = log.split(" ");
            if (split[1].equals("enter")) {
                map.put(split[0], split[1]);
                continue;
            }
            if (map.keySet().contains(split[0])) {
                map.remove(split[0]);
            }
        }
        List<String> result = map.keySet().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        for (String s : result) {
            System.out.println(s);
        }
    }
}
