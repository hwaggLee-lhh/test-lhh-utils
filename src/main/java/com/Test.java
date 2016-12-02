package com;

import java.util.Date;


public class Test extends Thread implements Runnable{

	public static void main(String[] args) {
		System.out.println(new Date(1433376000000l).toLocaleString());
				
	}
}
