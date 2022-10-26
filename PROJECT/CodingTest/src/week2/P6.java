package week2;

import java.util.*;
import java.util.stream.Collectors;

public class P6 {
    public static void main(String[] args) {
        P6 p6 = new P6();
        List<Participant> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int amount = scanner.nextInt();
        for (int i = 0; i < amount; i++) {
            String name = scanner.nextLine();
            String[] split = name.split(" ");
            new Participant(split[0], split[1], split[2]);
            list.add(new Participant(split[0], split[1], split[2]));
        }

    }

    static class Participant {
        int first;
        int second;
        int third;

        int score;

        public Participant(String first, String second, String third) {
            this.first = Integer.parseInt(first);
            this.second = Integer.parseInt(second);;
            this.third = Integer.parseInt(third);;
        }
    }


}
