package week2;

import java.util.Scanner;


public class P1 {
    public static void main(String[] args) {
        P1 p1 = new P1();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        String[] split = input.split(" ");
        int start = Integer.valueOf(split[0]);
        int finish = Integer.valueOf(split[1]);
        int row = p1.handleRowValue(start, finish);
        int column = p1.handleColumnValue(start, finish);

        System.out.println(row + column);
    }

    public int handleColumnValue(int start, int finish) {
        int startLocation;
        int finishLocation;
        if (check4(start)) {
            startLocation = 4;
        }else {
            startLocation = start % 4;
        }
        if (check4(finish)) {
            finishLocation = 4;
        }else {
            finishLocation = finish % 4;
        }

        return Math.abs(startLocation - finishLocation);
    }

    public int handleRowValue(int start, int finish) {
        int startLocation;
        int finishLocation;
        if (check4(start)) {
            startLocation = (start / 4) - 1;
        }else {
            startLocation = start / 4;
        }

        if (check4(finish)) {
            finishLocation = (finish / 4) - 1;
        }else {
            finishLocation = finish / 4;
        }
        return Math.abs(startLocation - finishLocation);
    }

    private boolean check4(int value) {
        if (value % 4 == 0) {
            return true;
        }
        return false;
    }
}
