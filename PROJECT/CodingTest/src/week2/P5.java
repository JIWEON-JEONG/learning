package week2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class P5 {
    public static void main(String[] args) {
        P5 p5 = new P5();
        Scanner scanner = new Scanner(System.in);
        List<String> inputList = new ArrayList();
        List<String> compareList = new ArrayList();
        String input = scanner.nextLine();
        for (int i = 0; i < Integer.valueOf(input); i++) {
            String name = scanner.nextLine();
            inputList.add(name);
            compareList.add(name);
        }
        if (p5.compare(inputList, compareList.stream().sorted().collect(Collectors.toList()))) {
            System.out.println("INCREASING");
        } else if (p5.compare(inputList, compareList.stream()
                .sorted(Comparator.reverseOrder()).collect(Collectors.toList()))) {
            System.out.println("DECREASING");
        } else {
            System.out.println("NEITHER");
        }
    }

    public boolean compare(List<String> input, List<String> sort) {
        if (input.equals(sort)) {
            return true;
        }
        return false;
    }
}
