package TIL.java.algo.string.string_orderby;

import java.util.Arrays;

public class StringOrderBy {
    public static void main(String[] args) {
        Solution solution = new Solution();
        String answer = solution.solution("affADa");

        System.out.println(answer);

    }

    static class Solution {
        public String solution(String s) {
            StringBuilder answer = new StringBuilder();

            int len = s.length();
            char[] arr = new char[len];

            for (int i = 0; i < len; i++) {
                char ascii = s.charAt(i);
                arr[i] = ascii;
            }

            Arrays.sort(arr);

            for (int i = len - 1; i >= 0; i--) {
                answer.append(arr[i]);
            }

            return answer.toString();
        }
    }
}
