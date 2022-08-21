package STUDY.TIL.java.practice.lang.wrapper;

public class WrapperPractice {
	public static void main(String[] args) {
		/*
		 * 자바5 이전까지 방식
		 */

		/*
		Integer num1 = new Integer(100);
		int num2 = 200;
		int sum = num1.intValue() + num2; //num1.intValue()(언박싱)
		Integer num3 = Integer.valueOf(num2); //Integer.valueOf(num2)(오토박싱)
		System.out.println(sum);
		System.out.println(num3);
		*/

		/*
		 * 자바5 이상부터는 변환 없이 사용할 수 있다.
		 */
		Integer num1 = new Integer(100);
		int num2 = 200;
		int sum = num1 + num2; //num1.intValue()(언박싱)
		Integer num3 = num2; //Integer.valueOf(num2)(오토박싱)
		System.out.println(sum);
		System.out.println(num3);
		
	}
}
