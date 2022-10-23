import java.util.Scanner;

/**
 * 1. 모음(a,e,i,o,u) 하나를 반드시 포함하여야 한다.
 * 2. 모음이 3개 혹은 자음이 3개 연속으로 오면 안 된다.
 * 3. 같은 글자가 연속적으로 두번 오면 안되나, ee 와 oo는 허용한다.
 */
public class P7 {

    public static Location kingLocation;
    public static Location rockLocation;
    class Location {

        char row;
        int column;

        public Location(String location) {
            this.row = location.charAt(0);
            this.column = Integer.valueOf(location.substring(1, 2));
        }

        public void addRowValue() {
            this.row += 1;
        }

        public void subRowValue() {
            this.row -= 1;
        }

        public void addColumnValue() {
            this.column += 1;
        }

        public void subColumnValue() {
            this.column -= 1;
        }
    }

    public static void main(String[] args) throws Exception {
        P7 P7 = new P7();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        kingLocation = P7.new Location(input.split(" ")[0]);
        rockLocation = P7.new Location(input.split(" ")[1]);
        int num = Integer.valueOf(input.split(" ")[2]);

        for (int i = 0; i < num; i++) {
            String order = scanner.nextLine();
            P7.handleOrder(order);
        }

        System.out.println(kingLocation.row + "" +  kingLocation.column);
        System.out.println(rockLocation.row + "" +rockLocation.column);

    }

    public void handleOrder(String order) throws Exception {
        if (order.equals("R")) {
            if (kingLocation.row == 'H') {
                return;
            }
            kingLocation.addRowValue();
            if (checkRockAndKingLocation()) {
                rockLocation.addRowValue();
            }
            return;
        } else if (order.equals("L")) {
            if (kingLocation.row == 'A') {
                return;
            }
            kingLocation.subRowValue();
            if (checkRockAndKingLocation()) {
                rockLocation.subRowValue();
            }
            return;
        } else if (order.equals("B")) {
            if (kingLocation.column == 1) {
                return;
            }
            kingLocation.subColumnValue();
            if (checkRockAndKingLocation()) {
                rockLocation.subColumnValue();
            }
            return;
        } else if (order.equals("T")) {
            if (kingLocation.column == 8) {
                return;
            }
            kingLocation.addColumnValue();
            if (checkRockAndKingLocation()) {
                rockLocation.addColumnValue();
            }
            return;
        } else if (order.equals("RT")) {
            if(kingLocation.row == 'H' || kingLocation.column == 8){
                return;
            }
            kingLocation.addRowValue();
            kingLocation.addColumnValue();
            if (checkRockAndKingLocation()) {
                rockLocation.addRowValue();
                rockLocation.addColumnValue();
            }
            return;
        } else if (order.equals("LT")) {
            if(kingLocation.row == 'A' || kingLocation.column == 8){
                return;
            }
            kingLocation.subRowValue();
            kingLocation.addColumnValue();
            if (checkRockAndKingLocation()) {
                rockLocation.subRowValue();
                rockLocation.addColumnValue();
            }
            return;
        } else if (order.equals("RB")) {
            if(kingLocation.row == 'H' || kingLocation.column == 1){
                return;
            }
            kingLocation.addRowValue();
            kingLocation.subColumnValue();
            if (checkRockAndKingLocation()) {
                rockLocation.addRowValue();
                rockLocation.subColumnValue();
            }
            return;
        } else if (order.equals("LB")) {
            if(kingLocation.row == 'A' || kingLocation.column == 1){
                return;
            }
            kingLocation.subRowValue();
            kingLocation.subColumnValue();
            if (checkRockAndKingLocation()) {
                rockLocation.subRowValue();
                rockLocation.subColumnValue();
            }
            return;
        }
        throw new Exception("invalid");
    }


    public boolean checkRockAndKingLocation() {
        if (kingLocation.row == rockLocation.row || kingLocation.column == rockLocation.column) {
            return true;
        }
        return false;
    }

}
