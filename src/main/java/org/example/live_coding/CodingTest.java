package org.example.live_coding;

import java.util.*;

class CodingTest {

    /**
     * 최적화 (그리디)
     * 100, 50, 5, 1
     */
    public int solution1(int score) {
        // 종류
        int[] types = {100, 50, 5, 1};
        int remaining = score;
        int total = 0;
        for (int type : types) {
            if (remaining == 0) {
                break;
            }
            int count = remaining / type;
            total += count;
            remaining %= type;
        }
        return total;
    }

    public boolean solution2(int capacity, int[][] routes) {
        List<int[]> events = new ArrayList<>();
        for (int[] r : routes) {
            int count = r[0], from = r[1], to = r[2];
            events.add(new int[]{from, count});
            events.add(new int[]{to, -count});
        }
        events.sort(Comparator
                .comparingInt((int[] e) -> e[0])
                .thenComparing(e -> e[1])
        );
        int currLoad = 0;
        for (int[] e : events) {
            currLoad += e[1];
            if (currLoad > capacity) {
                return false;
            }
        }
        return true;
    }

    /**
     * 특정 젤리 종류 (문자열) 이 전체 나머지보다 커지는 경우의 수가 존재하는지.
     * -
     */
    public int solution3(String[] pouches) {
        int n = pouches.length;
        int answer = 0;
        for (char c = 'a'; c <= 'e'; c++) {
            Integer[] counts = new Integer[n];
            for (int i = 0; i < n; i++) {
                String s = pouches[i];
                int countC = 0;
                for (char ch : s.toCharArray()) {
                    if (ch == c) countC++;
                }
                counts[i] = 2 * countC - s.length();
            }
            Arrays.sort(counts, Collections.reverseOrder());

            int sum = 0;
            int bestForChar = 0;
            for (int i = 0; i < n; i++) {
                sum += counts[i];
                if (sum > 0) {
                    bestForChar = i + 1;
                } else {
                    break;
                }
            }
            answer = Math.max(answer, bestForChar);
        }
        return answer;
    }
}
