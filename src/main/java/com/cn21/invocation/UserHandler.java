package com.cn21.invocation;

import java.lang.reflect.Proxy;

/**
 * @author cbx
 * 使用动态代理的例子
 */
public class UserHandler {

	public static void main( String[] args ) {
		// TODO Auto-generated method stub
		try {
			getMessage();
			useProxy();
        }
        catch( Exception e ) {
	        // TODO: handle exception
        	e.printStackTrace();
        }

	}
	
	public static void useProxy() throws Exception{
		String str = "Hello word";
		MyInvocationHandler myInvocationHandler = new MyInvocationHandler( str );
		ClassLoader cl = String.class.getClassLoader();
		Comparable obj = (Comparable)Proxy.newProxyInstance( cl, new Class[]{Comparable.class}, myInvocationHandler );
		System.out.println(obj.compareTo( "Good" ));
	}
	
	/**
	 * @throws Exception
	 * 网上动态代理的例子，利用了接口，不需要创建UserServiceImpl的实例，代理对象是UserServiceImpl,被代理的是UserService
	 */
	public static void getMessage() throws Exception{
		UserService userService = new UserServiceImpl();
		UserServiceInvocationHandler handler = new UserServiceInvocationHandler( userService );
		UserService userServiceProxy = (UserService)Proxy.newProxyInstance( userService.getClass().getClassLoader(),
			userService.getClass().getInterfaces(),handler);
		userServiceProxy.getName();
	}

}
