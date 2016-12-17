package com.cn21.invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class GreetHandler implements InvocationHandler{
	
	private GreetSecond greetSecond;
	
	public GreetHandler(GreetSecond greetSecond) {
	    // TODO Auto-generated constructor stub
		this.greetSecond = greetSecond;
    }

	@Override
    public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable {
	    // TODO Auto-generated method stub
		//判断该方法是否是getMessage方法
		if( "getMessage".equals(method.getName()) ) {
			Method g2 = GreetSecond.class.getMethod( method.getName(), new Class<?>[]{String.class} );
	        return g2.invoke( greetSecond, new Object[]{args[0]} );
        }
		return null;
    }

}
