package com.cn21.test;

import java.util.ArrayList;
import java.util.List;

/**
 * VM Args:-Xms20m -Xmx20m -XX:+HeapDompOnOutOfMemoryError
 * @author cbx
 * java堆内存溢出
 *
 */
public class Heapoom {
	
	public static class OOMbject{
		
	}

	public static void main( String[] args ) {
		// TODO Auto-generated method stub
		List<OOMbject> list = new ArrayList<Heapoom.OOMbject>();
		while( true ) {
	        list.add( new OOMbject() );
	        
        }

	}

}
