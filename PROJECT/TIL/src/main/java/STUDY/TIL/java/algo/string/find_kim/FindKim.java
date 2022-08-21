package STUDY.TIL.java.algo.string.find_kim;

import java.util.Arrays;

public class FindKim {

    public static void main(String[] args) {
        String[] param = {"abc", "Kim"};
        String solution = Solution.solution(param);
        System.out.println(solution);

    }

    static class Solution {
        public static String solution(String[] seoul) {
            String answer = "";

            Arrays.asList(seoul).indexOf("Kim");
            int num = -1;
            for (String s : seoul) {
                num++;
                if(s.equals("Kim")){
                    break;
                }
            }
            answer = "김서방은 " + num + "에 있다";
            return answer;
        }
    }
}
