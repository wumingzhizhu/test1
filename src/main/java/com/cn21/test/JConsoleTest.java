package com.cn21.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cbx jconsole例子
 */
public class JConsoleTest {

	// 64kB
	static class testObject {
		public byte[] temp = new byte[1024 * 64];
	}

	public static void fillHead( int num ) throws Exception {
		List<testObject> list = new ArrayList<JConsoleTest.testObject>();
		for( int i = 0; i < num; i++ ) {
			Thread.sleep( 50 );
			list.add( new testObject() );
		}
		System.gc();
	}

	public static void main( String[] args ) throws Exception {
		// TODO Auto-generated method stub
		fillHead( 1000 );
	}

}
