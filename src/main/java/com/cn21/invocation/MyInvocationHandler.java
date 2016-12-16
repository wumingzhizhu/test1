package com.cn21.invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author cbx 动态代理接口的实现
 */
public class MyInvocationHandler implements InvocationHandler {

	private Object proxy;

	public MyInvocationHandler(Object proxy) {
		this.proxy = proxy;
	}

	@Override
	public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println( "调用方法是:" + method.getName() + "参数是:" + Arrays.toString( args ) );
		//调用原始的方法,此proxy不是局部的变量，是私有变量Proxy，所以要用this关键字
		return method.invoke( this.proxy, args );
	}

}
