package com.yb.testapp;

public class TestApp {

	public static void main(String[] args) {
		sleep(400);
         System.out.println("hello !!");
         sleep(400);
         System.out.println("hello !!");
         sleep(400);
         System.out.println("hello !!");
         sleep(400);
         System.out.println("hello !!");
         sleep(400);
         System.out.println("hello !!");
         
	}

	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
