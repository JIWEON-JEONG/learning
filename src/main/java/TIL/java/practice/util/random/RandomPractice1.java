package TIL.java.practice.util.random;

import java.util.Random;

public class RandomPractice1 {

	public static void main(String[] args) {
		Random random = new Random(); // 랜덤 객체 생성
		
		random.setSeed(System.currentTimeMillis());
		
		System.out.println("0부터 N까지 무작위 랜덤 숫자 : " + random.nextInt(100));
		System.out.println("무작위 Int 값 : " + random.nextInt());
		System.out.println("무작위 Long 값 : " + random.nextLong());
		System.out.println("무작위 Double 값 : " + random.nextDouble());
		System.out.println("무작위 Float 값 : " + random.nextFloat());
		
		System.out.println("true, false : " + random.nextBoolean());
	}

}
