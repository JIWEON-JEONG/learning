package TIL.java.algo.sosu;

public class Sosu {

    public static void main(String[] args) {
        int solution = Solution.solution(10);
        System.out.println(solution);
    }
    static class Solution {
        public static int solution(int n) {
            int answer = 0;
            int val = (int) Math.sqrt(n);
            for (int i = 2; i <= n; i++) {
                if (n % i == 0) {
                    continue;
                }
                answer++;
            }
            return answer;
        }
    }
}
