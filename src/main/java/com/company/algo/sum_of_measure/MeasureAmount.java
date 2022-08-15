package com.company.algo.sum_of_measure;

public class MeasureAmount {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        int result = Solution.solution(n);
//
//        System.out.println(result);
//    }

    static class Solution {
        public static int solution(int n) {
            int answer = 0;
            int sqrt = (int) Math.sqrt(n);
            for (int i = 1; i <= sqrt; i++) {
                if (n % i == 0) {
                    answer += i;
                    int dividedValue = n / i;
                    if(i == dividedValue){
                        continue;
                    }
                    answer += dividedValue;
                }
            }
            return answer;
        }
    }
}
