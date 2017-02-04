package com.cn21.test;

public class test {

	public int add( int a ) {
		for( int i = 0; i < 1000000; i++ )
			;
		return a * 2;
	}

	public int otherAdd( int a ) {
		return a * 2;
	}

	public static void main( String[] args ) {
		/*Integer a = 1;
		Integer b = 2;
		Integer c = 3;
		Integer d = 4;
		Integer e = 321;
		Integer f = 321;
		Long g = 3L;
		System.out.println( c == d );
		System.out.println( e == f );
		System.out.println( c == (a + b) );
		System.out.println( c.equals( a + b ) );
		System.out.println( g == (a + b) );
		System.out.println( g.equals( a + b ) );*/
		test myTest = new test();
		long firstTime = System.currentTimeMillis();
		int sum = 0;
		for(int i =0;i < 100;i ++){
			sum += myTest.add( 1 );
		}
		System.out.println( System.currentTimeMillis() - firstTime );
		
		firstTime = System.currentTimeMillis();
		sum = 0;
		for(int i =0;i < 100;i ++){
			sum += myTest.add( 1 );
		}
		myTest.otherAdd( 1 );
		System.out.println( System.currentTimeMillis() - firstTime );
	}

}
