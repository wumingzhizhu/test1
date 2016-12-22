package com.cn21.invoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class TestInvoke {

	public static void main( String[] args ) {
		// TODO Auto-generated method stub
		try {
			// invokeExactTest();
			TestInvoke testInvoke = new TestInvoke();
			// testInvoke.TestAsVarargsCollector();
			// testInvoke.TestBindTo();
			testInvoke.TestOtherMethod();
		}
		catch( Throwable e ) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * @throws Throwable 方法句柄的例子
	 */
	public static void invokeExactTest() throws Throwable {
		MethodHandles.Lookup loopUp = MethodHandles.lookup();
		// 获取substring方法的类型，参数分别是返回类型，参数类型，参数类型
		MethodType type = MethodType.methodType( String.class, int.class, int.class );
		// 第一个参数的意思是在String.class中查找符合type类型（返回类型，参数类型）的方法
		MethodHandle mHandle = loopUp.findVirtual( String.class, "substring", type );
		String str = (String)mHandle.invokeExact( "Hello word", 1, 3 );
		System.out.println( str );

		MethodType type1 = MethodType.methodType( int.class, String.class );
		// 第一个参数的意思是在String.class中查找符合type类型（返回类型，参数类型）的方法,该方法的名字是"indexOf"
		MethodHandle mHandle1 = loopUp.findVirtual( String.class, "indexOf", type1 );
		int str1 = (int)mHandle1.invokeExact( "Helloword", "l" );
		System.out.println( str1 );
	}

	public void normalMethod( String arg1, int arg2, int[] arg3 ) {
		for( int i : arg3 ) {
			System.out.println( i );
		}

	}

	/**
	 * @throws Throwable 可变参数的调用
	 */
	public void TestAsVarargsCollector() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType( void.class, String.class, int.class, int[].class );
		MethodHandle methodHandle = lookup.findVirtual( TestInvoke.class, "normalMethod", type );
		// 转换后传参就不需要传数组，直接用可变参数即可
		methodHandle = methodHandle.asVarargsCollector( int[].class );
		// 直接使用，不用传数组
		methodHandle.invoke( this, "Hello", 1, 2, 3, 4 );
	}

	/**
	 * @throws Throwable 绑定参数和不绑定参数的调用
	 */
	public void TestBindTo() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType( int.class );
		MethodHandle methodHandle = lookup.findVirtual( String.class, "length", type );
		int len = (int)methodHandle.invoke( "hello" );
		System.out.println( "不绑定参数:" + len );
		methodHandle = methodHandle.bindTo( "hello word" );
		System.out.println( "绑定参数的调用: " + methodHandle.invoke() );
	}

	public void TestOtherMethod() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		// 调用构造方法
		String str = "baby";
		byte[] bt = str.getBytes();
		lookup.findConstructor( Mother.class, MethodType.methodType( void.class, byte[].class ) ).invoke( bt );
		// 调用静态方法
		lookup.findStatic( Mother.class, "getName", MethodType.methodType( void.class, Object[].class ) ).invoke( "hello", "world" );
		// 调用私有方法
		lookup.findSpecial( TestInvoke.class, "getAge", MethodType.methodType( void.class ), TestInvoke.class ).invoke(this);

	}
	
	//私有方法
	private void getAge() {
		System.out.println( "my age is 10" );
	}

}

class Mother {

	public Mother(byte[] name) {
		System.out.println( new String( name ) );
	}

	public static void getName( Object... object ) {
		for( Object object2 : object ) {
			System.out.println( object2 );
		}
	}

	private void getAge() {
		System.out.println( "my age is 10" );
	}
}
