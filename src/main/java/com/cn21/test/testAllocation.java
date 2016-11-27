package com.cn21.test;

/**VM参数 : -verbose:gc -Xms20M -Xmx20	M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * @author cbx
 *
 */
public class testAllocation {

	public static final int temp = 1024 * 1024;

	public static void main( String[] args ) {
		// TODO Auto-generated method stub
		byte[] allocation1, allocation2, allocation3, allocation4;
		allocation1 = new byte[2 * temp];
		allocation2 = new byte[2 * temp];
		allocation3 = new byte[2 * temp];
		// 出现一次 minor GC
		allocation4 = new byte[4 * temp];

	}

}
