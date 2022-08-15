package com.company.practice.exception;

public class ExceptionPractice3 {

	public static void main(String[] args) {
		try {
			String product = "apple";
			int productCnt = Integer.valueOf(product);
			
			//System.out.println(5 / 0);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());//간단하게
			//System.out.println(e.toString());//원인이유
			//e.printStackTrace();//발생한 이유와 위치는 어디에서 발생했는지 전체적인 단계를 다 출력
		}

	}

}
