package week2;

import java.util.*;

import java.util.Scanner;


public class P2 {
    public static void main(String[] args) {
        P2 p2 = new P2();
        List<String> resultList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        for (int i = 0; i < Integer.valueOf(input); i++) {
            String name = scanner.nextLine();
            String[] split = name.split(" ");
            split[0] = "god";
            String result = "";
            StringBuilder builder = new StringBuilder(result);
            for (String s : split) {
                builder.append(s);
            }
            resultList.add(String.valueOf(builder));
        }
        for (String s : resultList) {
            System.out.println(s);
        }
    }
}