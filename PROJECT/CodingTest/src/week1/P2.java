package week1;

import java.util.Scanner;

public class P2 {

    //[n-1][n]
    public static final String[][] data = {{"A", "C", "A", "G"}, {"C", "G", "T", "A"}, {"A", "T", "C", "G"}, {
            "G", "A", "G", "T"}};

    public static void main(String[] args) throws Exception {
        P2 P2 = new P2();
        Scanner scanner = new Scanner(System.in);

        int size = Integer.valueOf(scanner.nextLine());
        String val = scanner.nextLine();

        String lastValue = val.substring(size - 1);

        for (int i = size - 2; i > -1; i--) {
            String compareValue = val.substring(i,i+1);
            lastValue = data[P2.changeInteger(compareValue)][P2.changeInteger(lastValue)];
        }

        System.out.println(lastValue);
    }

    public int changeInteger(String value) throws Exception {
        if (value.equals("A")) {
            return 0;
        } else if (value.equals("G")) {
            return 1;
        } else if (value.equals("C")) {
            return 2;
        }
        else if (value.equals("T")) {
            return 3;
        }
        throw new Exception("invalid");
    }


}
