package com.cn21.test;

/**
 * @author cbx
 * VM参数 : -verbose:gc -Xms20M -Xmx20	M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
 */
public class testTenuringThreshold {

	public static final int temp = 1024 * 1024;

	public static void main( String[] args ) {
		// TODO Auto-generated method stub
		byte[] allocation1, allocation2, allocation3;
		allocation1 = new byte[temp / 4];
		allocation2 = new byte[4 * temp];
		allocation3 = new byte[4 * temp];
		
		allocation3 = null;
		allocation3 = new byte[4 * temp];
	}

}
