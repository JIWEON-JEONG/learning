package STUDY.TIL.java.practice.lang.string;

public class StringBufferBuilder {

	public static void main(String[] args) {
		StringBuilder sb2 = new StringBuilder();
		//StringBuffer sb = new StringBuffer();

		sb2.append("Java "); //append 문자열 추가
		sb2.append("Program Study");
		System.out.println(sb2.toString());
		
		sb2.insert(4, "2");//index에 삽입
		System.out.println(sb2.toString());

		sb2.setCharAt(4, '6');//문자 교체
		System.out.println(sb2.toString());
		
		sb2.replace(6, 13, "Book");//문장 교체
		System.out.println(sb2.toString());
		
		sb2.delete(4, 5);//매개 변수로 전달받은 인덱스 사이의 문자열 제거
		System.out.println(sb2.toString());
		
		int length = sb2.length();//길이
		System.out.println("총문자수: " + length);
		
		String result = sb2.toString();//String형으로
		System.out.println(result);		
		
	}

}
