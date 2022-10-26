package week2;

import org.w3c.dom.Node;

import java.util.*;

public class P3 {
    public static void main(String[] args) {
        P3 p3 = new P3();
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> result = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] split = input.split(" ");
        int totalP = Integer.parseInt(split[0]);
        int value = Integer.parseInt(split[1]);

        for (int i = 1; i <= totalP; i++) {
            map.put(i, i);
        }



    }


}
