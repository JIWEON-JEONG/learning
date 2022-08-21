package STUDY.TIL.java.practice.util.random;

import java.util.Random;

public class RandomPractice2 {

	public static void main(String[] args) {
		Random random = new Random(); // 랜덤 객체 생성
		
		random.setSeed(0);
		System.out.println("첫번째 랜덤 숫자");
		for (int i = 0; i < 5; i++) {
			System.out.print(random.nextInt(100) + " ");
		}
		System.out.println();
		
		System.out.println("두번째 랜덤 숫자");
		random.setSeed(0);
		for (int i = 0; i < 5; i++) {
			System.out.print(random.nextInt(100) + " ");
		}
		
		//계속 바뀌는값? -> System.currentTimeMillis()
	}

}
