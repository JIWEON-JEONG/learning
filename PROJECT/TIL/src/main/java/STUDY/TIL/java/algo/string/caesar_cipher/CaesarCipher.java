package STUDY.TIL.java.algo.string.caesar_cipher;

import java.util.Scanner;

public class CaesarCipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String beforeCaesar = scanner.nextLine();
        int pushValue = scanner.nextInt();
        String result = Solution.solution(beforeCaesar,pushValue);

        System.out.println(result);
    }
    static class Solution {
        public static String solution(String s, int n) {
            String answer = "";
            for (int i = 0; i < s.length(); i++) {
                char asciiValue = s.charAt(i);
                if(asciiValue != 32){
                    asciiValue += n;
                }
                if (asciiValue < 97 && asciiValue > 90) {
                    asciiValue = (char) (asciiValue - 26);
                }
                if (asciiValue > 122){
                    asciiValue = (char) (asciiValue - 26);
                }
                answer += String.format(String.valueOf(asciiValue));
            }
            return answer;
        }
    }
}

