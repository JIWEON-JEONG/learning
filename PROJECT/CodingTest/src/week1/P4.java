package week1;

import java.util.ArrayList;
import java.util.Scanner;


public class P4 {

    public static void main(String[] args) throws Exception {
        P4 P4 = new P4();
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> list = new ArrayList();
        int sum = 0;
        int notRegisterSum = 0;
        int num = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < num; i++) {
            int val = scanner.nextInt();
            sum += val;
            list.add(val);
        }
        for (int i = 0; i < num; i++) {
            int val = scanner.nextInt();
            if (val == 0) {
                notRegisterSum += list.get(i);
            }
        }

        System.out.println(sum);
        System.out.println(notRegisterSum);
        
    }

}
