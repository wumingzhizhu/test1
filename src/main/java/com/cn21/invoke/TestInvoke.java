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
			// testInvoke.TestOtherMethod();
			// testInvoke.dropArguments();
			// testInvoke.insertArguments();
			// testInvoke.filterArguments();
			// testInvoke.foldArguments();
			// testInvoke.permuteArguments();
			//testInvoke.guardWithTest();
			testInvoke.invokerTransFrom();
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
		lookup.findSpecial( TestInvoke.class, "getAge", MethodType.methodType( void.class ), TestInvoke.class ).invoke( this );

	}

	/**
	 * @throws Throwable 方法句柄的转换，该方法会添加一些额外的没用的参数。
	 */
	public void dropArguments() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType( String.class, int.class, int.class );
		MethodHandle oldHandle = lookup.findVirtual( String.class, "substring", type );
		System.out.println( oldHandle.invoke( "hello", 0, 2 ) );
		// dropArguments方法可以加入一些无用的参数
		MethodHandle newHandle = MethodHandles.dropArguments( oldHandle, 0, String.class, float.class );
		System.out.println( newHandle.invoke( "message", 0.5f, "hello", 0, 2 ) );
	}

	/**
	 * @throws Throwable 对方法句柄绑定具体的值
	 */
	public void insertArguments() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType( String.class, String.class );
		MethodHandle oldHandle = lookup.findVirtual( String.class, "concat", type );
		System.out.println( oldHandle.invoke( "hello", "word" ) );
		// 指定参数是“--”
		MethodHandle newHandle = MethodHandles.insertArguments( oldHandle, 1, "--" );
		System.out.println( newHandle.invoke( "hello" ) ); // 输出 "hello--"

	}

	/**
	 * @throws Throwable 对方法句柄的参数进行预处理，再传入真正的句柄
	 */
	public void filterArguments() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType( int.class, int.class, int.class );
		// 预处理的句柄
		MethodHandle mGetLengthHandle = lookup.findVirtual( String.class, "length", MethodType.methodType( int.class ) );
		MethodHandle tarHandle = lookup.findStatic( Math.class, "max", type );
		MethodHandle newHandle = MethodHandles.filterArguments( tarHandle, 0, mGetLengthHandle, mGetLengthHandle );
		// 入参会先经过预处理的句柄，再到达真正的句柄
		System.out.println( newHandle.invoke( "hello", "world is me" ) );
	}

	public void foldArguments() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle combiner = lookup.findStatic( Math.class, "max", MethodType.methodType( int.class, int.class, int.class ) );
		MethodHandle target = lookup.findStatic( TestInvoke.class, "getFirst",
		    MethodType.methodType( int.class, int.class, int.class, int.class ) );
		MethodHandle mResultHandle = MethodHandles.foldArguments( target, combiner );
		// 传参3,4,先经过combiner的MAX方法，得到4，作为新的参数，传到targer句柄，参数列表为4,3,4,getFirst方法返回第一个参数，所以为4
		System.out.println( mResultHandle.invoke( 3, 4 ) );

	}

	public void permuteArguments() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType( int.class, int.class, int.class );
		MethodHandle mCompareHandle = lookup.findStatic( Integer.class, "compare", type );
		System.out.println( mCompareHandle.invoke( 3, 4 ) );
		// 调用permuteArguments可以对打乱参数的顺序，例如下面可以颠倒参数,参数1代表原来参数列表的第二个参数，0代表原来参数列表的第一个参数
		MethodHandle mNew = MethodHandles.permuteArguments( mCompareHandle, type, 1, 0 );
		System.out.println( mNew.invoke( 3, 4 ) );
	}

	/**
	 * @throws Throwable 用来进行判断的方法句柄，一共需要3个句柄，一个用来判断，另外两个是为真和假需要用的
	 */
	public void guardWithTest() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle judgethHandle = lookup.findStatic( TestInvoke.class, "judgeTest", MethodType.methodType( boolean.class ) );
		MethodHandle trueHandle = lookup.findStatic( Math.class, "max", MethodType.methodType( int.class, int.class, int.class ) );
		MethodHandle falseHandle = lookup.findStatic( Math.class, "min", MethodType.methodType( int.class, int.class, int.class ) );
		MethodHandle mHandle = MethodHandles.guardWithTest( judgethHandle, trueHandle, falseHandle );
		System.out.println( mHandle.invoke( 3, 5 ) );

	}

	/**
	 * @throws Throwable
	 * 元方法句柄，通过invoker可以调用其他的方法句柄
	 */
	public void invokerTransFrom() throws Throwable {
		MethodType methodType = MethodType.methodType( String.class, String.class, int.class, int.class );
		MethodHandle invoker = MethodHandles.exactInvoker( methodType );
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle toUpHandle = lookup.findVirtual( String.class, "toUpperCase", MethodType.methodType( String.class ) );
		invoker = MethodHandles.filterReturnValue( invoker, toUpHandle );
		MethodHandle mlHandle = lookup.findVirtual( String.class, "substring",
		    MethodType.methodType( String.class, int.class, int.class ) );
		System.out.println( invoker.invoke( mlHandle, "hello", 3, 4 ) );
	}

	public static boolean judgeTest() {
		return Math.random() > 0.5;
	}

	// 私有方法
	private void getAge() {
		System.out.println( "my age is 10" );
	}

	public static int getFirst( int arg0, int arg1, int arg2 ) {
		return arg0;
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
