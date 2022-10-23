package TIL.java.practice.lang.equals;

public class main {

	public static void main(String[] args) {
		System.out.println("[일반 객체]");
		Student st1 = new Student("leeyebin", 34);
		Student st2 = new Student("leeyebin", 34);
		
		System.out.println(st1 == st2);// 주소값만 비교
		System.out.println(st1.equals(st2)); // 
		
		System.out.println();
		
		
		System.out.println("[String 객체]");
		
		String s1 = new String("Test");
		String s2 = new String("Test");
		
		System.out.println(s1 == s2);// false
		System.out.println(s1.equals(s2)); // true
		
		System.out.println();
		
		System.out.println("[Integer 객체]");
		Integer i1 = new Integer(10);
		Integer i2 = new Integer(10);
		
		System.out.println(i1 == i2);// false
		System.out.println(i1.equals(i2)); // true
		

	}

}
