package onboarding;

//리팩토링 필요.
public class Problem3 {
    public static int solution(int number) {
        Problem3 p3 = new Problem3();
        return p3.getTotalClap(number);
    }

    public int getTotalClap(int number) {
        int totalClap = 0;
        for (int i = 1; i < number + 1; i++) {
            totalClap += check369(i);
        }
        return totalClap;
    }

    public int check369(int num) {
        int count = 0;
        String value = String.valueOf(num);
        for (char c : value.toCharArray()) {
            if (c == '3' || c == '6' || c == '9') {
                count++;
            }
        }
        return count;
    }

}
