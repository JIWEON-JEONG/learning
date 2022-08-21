package STUDY.TIL.java.algo.string;

import java.util.Arrays;

//문자열 s의 길이가 4 혹은 6이고, 숫자로만 구성돼있는지 확인해주는 함수, solution을 완성하세요. \
// 예를 들어 s가 "a234"이면 False를 리턴하고 "1234"라면 True를 리턴하면 됩니다.
public class Ex01 {
    public void main(String[] args) {
        Solution solution = new Solution();
        solution.solution("fadkl");
    }

    class Solution {
        public boolean solution(String s) {
            boolean answer = false;
            int len = s.length();

            if (len == 4 || len == 6) {
                char[] arr = s.toCharArray();

                Arrays.sort(arr);

                if (arr[0] < 48) {
                    return answer;
                }

                if (arr[len - 1] > 57) {
                    return answer;
                }

                answer = true;
            }
            return answer;
        }
    }
}


class Solution {
    public boolean solution(String s) {
        if(s.length() == 4 || s.length() == 6){
            try{
                int x = Integer.parseInt(s);
                return true;
            } catch(NumberFormatException e){
                return false;
            }
        }
        else return false;
    }
}
