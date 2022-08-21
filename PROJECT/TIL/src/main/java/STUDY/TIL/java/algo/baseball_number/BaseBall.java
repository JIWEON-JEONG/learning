package STUDY.TIL.java.algo.baseball_number;

import java.util.ArrayList;
import java.util.Stack;

public class BaseBall {

//    public static void main(String[] args) {
//        int baseball[][] = {{123, 1, 1}, {356, 1, 0}, {327, 2, 0}, {489, 0, 1}};
//        BaseBall T = new BaseBall();
//        int solution = T.solution(baseball);
//        System.out.println(solution);
//    }

    //가능한 답의 개수 리턴
    //baseball [n][세자리의수, 스트라이크 수 , 볼의 수]
    public int solution(int baseball[][]){

        //한글자씩 봐야 하기 때문에
        Stack<String> stackList = new Stack<>();
        Stack<String> answerList = new Stack<>();
        //모든 경우의 수들 스택에 저장
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                for (int k = 1; k < 10; k++) {
                    if (i != j && j != k && i != k) {
                        stackList.add(String.valueOf(i * 100 + j * 10 + k));
                    }
                }
            }
        }

        while (!stackList.isEmpty()){
            boolean flag = true;

            String out = stackList.pop();

            for (int i = 0; i < baseball.length; i++) {

                int strike = handleStrike(out, String.valueOf(baseball[i][0]));
                int ball = handleBall(out, String.valueOf(baseball[i][0])) - strike;

                //or 연산이 그나마 빠를듯
                if(strike != baseball[i][1] || ball != baseball[i][2]){
                    flag = false;
                    break;
                }
            }

            if(flag == true){
                answerList.add(out);
            }
        }

        return answerList.size();
    }

    public int handleStrike(String n1, String n2){
        int result = 0;
        for (int i = 0; i < 3; i++) {
            if(n1.charAt(i) == n2.charAt(i)){
                result++;
            }
        }
        return result;
    }

    public int handleBall(String n1, String n2){
        ArrayList<Character> list = new ArrayList<>();
        int result = 0;
        for (int i = 0; i < 3; i++) {
            list.add(n2.charAt(i));
        }
        for (int i = 0; i < 3; i++) {
            if(list.contains(n1.charAt(i))){
                result++;
            }
        }
        return result;
    }
}
