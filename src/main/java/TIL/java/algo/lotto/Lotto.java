package TIL.java.algo.lotto;

public class Lotto {

    public static void main(String[] args) {

        int[] lottos = {44, 1, 0, 0, 31, 25};
        int[] win_nums = {31, 10, 45, 1, 6, 19};


        int[] solution = Solution.solution(lottos,win_nums);
    }
    static class Solution {
        public static int[] solution(int[] lottos, int[] win_nums) {
            //lottos -> 구매한 lottos 주워지는 .
            //win_nums -> 당첨번호
            Solution solution = new Solution();
            int[] answer = new int[2];
            int max = solution.handleMaxRate(lottos, win_nums);
            int min = solution.handleMinRate(lottos, win_nums);

            System.out.println(max + "," +  min);
            switch (max) {
                case 2:
                    answer[0] = 5;
                    break;
                case 3:
                    answer[0] = 4;
                    break;
                case 4:
                    answer[0] = 3;
                    break;
                case 5:
                    answer[0] = 2;
                    break;
                case 6:
                    answer[0] = 1;
                    break;
                default:
                    answer[0] = 6;
            }

            switch (min) {
                case 2:
                    answer[1] = 5;
                    break;
                case 3:
                    answer[1] = 4;
                    break;
                case 4:
                    answer[1] = 3;
                    break;
                case 5:
                    answer[1] = 2;
                    break;
                case 6:
                    answer[1] = 1;
                    break;
                default:
                    answer[1] = 6;
            }

            return answer;
        }

        public int handleMaxRate(int[] lottos, int[] win_nums) {
            int count = 0;
            boolean flag = true;
            for (int win_num : win_nums) {
                for (int lotto : lottos) {
                    if (win_num == lotto) {
                        count++;
                    } else if (lotto == 0 && flag == true) {
                        count++;
                    }
                }
                flag = false;
            }
            return count;
        }

        public int handleMinRate(int[] lottos, int[] win_nums) {
            int count = 0;
            for (int win_num : win_nums) {
                for (int lotto : lottos) {
                    if (win_num == lotto) {
                        count++;
                    }
                }
            }
            return count;
        }
    }
}

class Solution {
    public int[] solution(int[] lottos, int[] win_nums) {
        int[] answer = new int[2];

        int cnt1 = 0;
        int cnt2 = 0;
        for(int i : lottos) {
            if(i == 0) {
                cnt1++;
                continue;
            }
            for(int j : win_nums) {
                if(i == j) cnt2++;
            }
        }


        answer[0] = getGrade(cnt1+cnt2);
        answer[1] = getGrade(cnt2);

        return answer;
    }

    public int getGrade(int n) {
        switch(n) {
            case 6 :
                return 1;
            case 5 :
                return 2;
            case 4 :
                return 3;
            case 3 :
                return 4;
            case 2 :
                return 5;
            default :
                return 6;
        }
    }
}
