package com.utils;

public class UtilsThread {

	public static void main(String[] args) {
		_thread_join();
		_thread_join();
	}
	
	/**
	 * 线程停止
	 * 
	 */
	public synchronized static void _thread_join(){
		System.out.println("---->准备开启线程");
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				System.out.println("---->线程运行中");
				try {
					System.out.println("---->休眠中");
					Thread.sleep(5*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally{
					System.out.println("---->睡醒");
				}
				System.out.println("---->线程结束");
			}
		});
		t1.start();
		try {
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("---->开启线程");
	}
}
