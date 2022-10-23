package TIL.java.oop;

import java.util.List;

public class Pos {
    private int toPay = 0;

    public int calcToPay(List<String> breadList, List<String> drinkList){
        for (String bread : breadList) {
            toPay += bread.length() * 1000;
        }
        for (String drink : drinkList) {
            toPay += drink.length() * 1000;
        }

        return toPay;
    }
}
