package com.company.practice.lang.string;

public class StringDecompile {

	public static void main(String[] args) {
		String aa = "BB";
		aa = "AA" + aa + "CC";
		aa = aa + "DD";
		aa = aa + "EE";
		
		System.out.println(aa);
	}

}
