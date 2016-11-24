package com.cn21.test;

public class FinalizeEscpaceGC {

	public static FinalizeEscpaceGC SAVE_US = null;

	public void isAlive() {
		System.out.println( "是，我还存活着" );
	}

	@Override
	public void finalize() throws Throwable {
		super.finalize();
		System.out.println( "finalize方法执行完毕" );
		FinalizeEscpaceGC.SAVE_US = this;
	}

	public static void main( String[] args ) throws Throwable {
		// TODO Auto-generated method stub
		SAVE_US = new FinalizeEscpaceGC();
		// 对象第一次成功拯救自己
		SAVE_US = null;
		System.gc();
		// 因为finalize方法优先级很低，所以sleep0.5s等待它
		Thread.sleep( 500 );
		if( SAVE_US != null ) {
			SAVE_US.isAlive();
		}
		else {
			System.out.println("我已经被回收了");
		}
		
		//执行第二次，就不能拯救自己了
		// 对象第一次成功拯救自己
		SAVE_US = null;
		System.gc();
		// 因为finalize方法优先级很低，所以sleep0.5s等待它
		Thread.sleep( 500 );
		if( SAVE_US != null ) {
			SAVE_US.isAlive();
		}
		else {
			System.out.println("我已经被回收了");
		}
	}

}
