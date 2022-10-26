package week2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class P7 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Double> l = new ArrayList<>();
        String[] input = scanner.nextLine().split(" ");
        int amount = Integer.parseInt(input[0]);
        int value = Integer.parseInt(input[1]);
        for (int i = 0; i < amount; i++) {
            double nm = scanner.nextDouble();
            l.add(nm);
        }

        List<Double> list = l.stream().sorted().collect(Collectors.toList());

        double avgJ = 0;
        //절사 평균
        for (int i = value; i < amount - value; i++) {
            avgJ += list.get(i);
        }
        avgJ = avgJ / (amount - (value * 2));
        String[] j = String.valueOf(avgJ).split("\\.");
        StringBuilder builderJ = new StringBuilder(j[0]);
        builderJ.append(".");
        if (j[1].length() > 2) {
            builderJ.append(j[1].charAt(0));
            if (j[1].charAt(1) < 5) {
                builderJ.append(j[1].charAt(1));
            } else {
                String num = String.valueOf(j[1].charAt(1));
                int val = Integer.valueOf(num) + 1;
                builderJ.append(val);
            }
        } else if (j[1].length() == 0) {
            builderJ.append("00");
        } else if (j[1].length() == 1) {
            builderJ.append(j[1].charAt(0));
            builderJ.append("0");
        } else builderJ.append(j[1]);

        double avgB = 0;
        //보정평균
        for (int i = 0; i < value; i++) {
            avgB += list.get(value);
        }
        for (int i = value; i < amount - value; i++) {
            avgB += list.get(i);
        }
        for (int i = amount - value; i < amount; i++) {
            avgB += list.get(amount - value - 1);
        }
        avgB = avgB / amount;
        String[] b = String.valueOf(avgB).split("\\.");
        StringBuilder builderB = new StringBuilder(b[0]);
        builderB.append(".");
        if (b[1].length() > 2) {
            builderB.append(b[1].charAt(0));
            if (b[1].charAt(1) < 5) {
                builderB.append(b[1].charAt(1));
            } else {
                String num = String.valueOf(b[1].charAt(1));
                int val = Integer.valueOf(num) + 1;
                builderB.append(val);
            }
        } else if (b[1].length() == 0) {
            builderB.append("00");
        } else if (b[1].length() == 1) {
            builderB.append(b[1].charAt(0));
            builderB.append("0");
        } else builderB.append(b[1]);

        System.out.println(builderJ);
        System.out.println(builderB);
    }
}
