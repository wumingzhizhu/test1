package com.cn21.invocation;

import java.lang.reflect.Proxy;

/**
 * @author cbx 使用动态代理的例子
 */
public class UserHandler {

	public static void main( String[] args ) {
		// TODO Auto-generated method stub
		try {
			//getMessage();
			//useProxy();
			//TestCommonProxy();
			changeToSecond();
		}
		catch( Exception e ) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public static void useProxy() throws Exception {
		String str = "Hello word";
		MyInvocationHandler myInvocationHandler = new MyInvocationHandler( str );
		ClassLoader cl = String.class.getClassLoader();
		Comparable obj = (Comparable)Proxy.newProxyInstance( cl, new Class[] { Comparable.class }, myInvocationHandler );
		System.out.println( obj.compareTo( "Good" ) );
	}

	/**
	 * @throws Exception 网上动态代理的例子，利用了接口
	 */
	public static void getMessage() throws Exception {
		UserService userService = new UserServiceImpl();
		UserServiceInvocationHandler handler = new UserServiceInvocationHandler( userService );
		UserService userServiceProxy = (UserService)Proxy.newProxyInstance( userService.getClass().getClassLoader(), userService
		        .getClass().getInterfaces(), handler );
		userServiceProxy.getName();
	}

	/**
	 * @param inf
	 * @param object
	 * @return
	 * 为任何的接口以及实现类提供代理
	 */
	public static <T> T commonProxy( final T object ) {
		MyInvocationHandler handler = new MyInvocationHandler( object );
		ClassLoader classLoader = object.getClass().getClassLoader();
		return (T)Proxy.newProxyInstance( classLoader, object.getClass().getInterfaces(), handler );
	}
	
	/**
	 * @throws Exception
	 * 测试方法
	 */
	public static void TestCommonProxy() throws Exception{
		FatherService fatherService = new FatherServiceImpl();
		commonProxy( fatherService ).getName();
		commonProxy( fatherService ).getAge();
		MatherService matherService = new MatherServiceImpl();
		commonProxy( matherService ).getAddress();
		commonProxy( matherService ).getCharacter();
	}
	
	/**
	 * 如果调用greetFrist的方法，会自动变成greetSecond方法
	 */
	public static void changeToSecond(){
		GreetSecond greetSecond = new GreetSecondImpl();
		GreetHandler handler = new GreetHandler( greetSecond );
		ClassLoader cl = greetSecond.getClass().getClassLoader();
		GreetFirst second = (GreetFirst)Proxy.newProxyInstance( cl, new Class<?>[]{GreetFirst.class}, handler );
		String name = second.getMessage( "李连杰","12345" );
		System.out.println(name);
	}

}
