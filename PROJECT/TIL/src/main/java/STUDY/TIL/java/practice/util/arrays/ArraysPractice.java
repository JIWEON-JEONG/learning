package STUDY.TIL.java.practice.util.arrays;

import java.util.Arrays;

public class ArraysPractice {

	public static void main(String[] args) {
		/*
		 * Arrays.sort(배열)
		 */
		int[] arr = {5, 3, 4, 1, 2};
		Arrays.sort(arr);//정렬 메소드(오름차순 정렬)
		
		for(int i=0; i<arr.length; i++){
		    System.out.print(arr[i] + " ");
		}
		System.out.println();
		
		/*
		 * Arrays.fill(배열, 매개변수) 메소드
		 */
		int[] arr1 = new int[10];
		/*
		for(int k=0; k<10; k++) {
			arr1[k] = 1;
		}
		*/
		Arrays.fill(arr1, 1);
		for (int i = 0; i < arr1.length; i++) {
		    System.out.print(arr1[i] + " ");
		}
	}

}
