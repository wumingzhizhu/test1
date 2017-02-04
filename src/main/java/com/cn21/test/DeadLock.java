package com.cn21.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author cbx 死锁时jconsole的情况
 */
public class DeadLock {

	/**
	 * 线程死锁循环
	 */
	public static void createBusyThread() {
		Thread thread = new Thread( new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				while( true )
					;
			}
		}, "testBusyThread" );
		thread.start();
	}

	/**
	 * @param lock 线程等待演示
	 */
	public static void createLockThread( final Object lock ) {
		Thread thread = new Thread( new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				synchronized( lock ) {
					try {
						lock.wait();
					}
					catch( Exception e ) {
						e.printStackTrace();
					}
				}
			}
		}, "testLockThread" );
		thread.start();
	}

	/**
	 * @author cbx 可以检测到的线程死锁
	 */
	static class SynAddRunnable implements Runnable {

		int a, b;

		public SynAddRunnable(int a, int b) {
			this.a = a;
			this.b = b;
		}

		public void run() {
			// TODO Auto-generated method stub
			synchronized( Integer.valueOf( a ) ) {
				synchronized( Integer.valueOf( b ) ) {
					System.out.println( a + b );
				}
			}

		}

	}

	public static void main( String[] args ) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
		reader.readLine();
		/*
		 * createBusyThread(); reader.readLine(); Object object = new Object();
		 * createLockThread( object );
		 */
		for( int i = 0; i < 100; i++ ) {
			new Thread( new SynAddRunnable( 1, 2 ) ).start();
			new Thread( new SynAddRunnable( 2, 1 ) ).start();
		}
	}

}
