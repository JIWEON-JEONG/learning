package STUDY.TIL.java.practice.util.objects;

import java.util.Objects;

public class ObjectsPractice {

	public static void main(String[] args) {
		/*
		 * Objects.toString(객체)
		 */
		Integer a = null;

		//System.out.println(a.toString());
	    //System.out.println(Objects.toString(a));

		
		/*
		 * Objects.isNull(객체)
		 */
		String b = new String("apple");
		
		System.out.println("Objects.isNull(a) : " + Objects.isNull(a));
		System.out.println("Objects.isNull(b) : " + Objects.isNull(b));
		System.out.println("Objects.nonNull(a) : " + Objects.nonNull(a));
		System.out.println("Objects.nonNull(b) : " + Objects.nonNull(b));
		
	}

}
