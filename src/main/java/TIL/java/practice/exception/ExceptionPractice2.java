package TIL.java.practice.exception;

import java.io.IOException;

public class ExceptionPractice2 {

	public static void main(String[] args) {
		byte[] list = { 'a', 'b', 'c' };

		try {
			System.out.write(list);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
