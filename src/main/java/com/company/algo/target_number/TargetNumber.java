package com.company.algo.target_number;

import java.util.ArrayList;

public class TargetNumber {
    //DFS 이용
    ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

    public static void main(String[] args) {
        int[] ins = {1, 2, 3};
        TargetNumber T = new TargetNumber();
        T.setGraph(ins);

    }

    public void setGraph(int[] numbers) {
        for (int number : numbers) {
            graph.add(new ArrayList<>());
            
        }
    }



    class Solution {
        public int solution(int[] numbers, int target) {
          return 1;
        }
    }
}
