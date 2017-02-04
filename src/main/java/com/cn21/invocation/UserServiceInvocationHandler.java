package com.cn21.invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UserServiceInvocationHandler implements InvocationHandler{
	
	private Object object;
	
	public UserServiceInvocationHandler(Object object) {
	    // TODO Auto-generated constructor stub
		this.object = object;
    }

	@Override
    public Object invoke( Object object, Method method, Object[] arg2 ) throws Throwable {
	    // TODO Auto-generated method stub
		//调用原来的方法
	    return method.invoke( this.object, arg2 );
    }

}
