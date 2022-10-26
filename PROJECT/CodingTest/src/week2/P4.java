package week2;

import java.util.ArrayList;
import java.util.Scanner;


public class P4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String input = scanner.next();
            char[] chars = input.toCharArray();
            char temp = chars[0];
            chars[0] = chars[1];
            chars[1] = temp;
            StringBuilder builder = new StringBuilder();
            for (char aChar : chars) {
                builder.append(aChar);
            }
            result.add(String.valueOf(builder));
        }

        for (String s : result) {
            System.out.println(s);
        }

    }
}
