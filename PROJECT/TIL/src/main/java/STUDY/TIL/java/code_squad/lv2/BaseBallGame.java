package STUDY.TIL.java.code_squad.lv2;

import java.io.IOException;
import java.util.*;

public class BaseBallGame {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        BaseBallGame game = new BaseBallGame();
        Answer answerObj = new Answer(game.answerInit().toCharArray());

        while(true) {
            System.out.println("값 입력 : >> ");
            char[] number = (scanner.nextLine()).toCharArray();
            game.handleInputException(number);
            game.calcBallAndStrike(answerObj,number);
            if (answerObj.verifyAnswer()){ return;}
            game.retry(answerObj);
        }
    }

    public void calcBallAndStrike(Answer answer,char[] number){
        answer.calcStrike(number);
        answer.calcBall(number);
    }

    public void retry(Answer answer){
        answer.print();
        answer.init();
    }

    public String answerInit() {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            int num = random.nextInt(10);
            stringBuilder.append(num);
        }
        return stringBuilder.toString();
    }

    public void handleInputException(char[] input) throws IOException {
        if (input.length != 3) {
            throw new IOException("입력값의 길이를 확인해 주세요.");
        }
        for (char c : input) {
            if (c < 48 || c > 57) {
                throw new IOException("입력값은 숫자 여야 합니다.");
            }
        }
    }

}

class Answer {
    char[]  answer;
    int strike;
    int ball;

    public Answer(char[] answer) {
        this.answer = answer;
    }

    public void calcStrike(char[] number) {
        for (int i = 0; i < answer.length; i++) {
            if (answer[i] == number[i]) {
                strike++;
            }
        }
    }

    public void calcBall(char[] number) {
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {list.add(answer[i]);}
        for (int i = 0; i < 3; i++) {
            if(list.contains(number[i])){
                Character value = number[i];
                list.remove(value);
                ball++;
            }
        }
        ball = ball - strike;
    }

    public void init() {
        this.strike = 0;
        this.ball = 0;
    }

    public boolean verifyAnswer() {
        if (strike == 3) {
            System.out.println("3개의 숫자를 모두 맞히셨습니다!");
            System.out.print(" - 게임 종료 -");
            return true;
        } else return false;
    }
    public void print() {
        if (strike == 0 && ball == 0) {
            System.out.println("0 스트라이크 , 0 볼");
        } else if (strike == 0) {
            System.out.println(ball + " 볼");
        } else if (ball == 0) {
            System.out.println(strike + " 스트라이크");
        } else {
            System.out.println(strike + " 스트라이크" + ball + " 볼");
        }
    }
}
