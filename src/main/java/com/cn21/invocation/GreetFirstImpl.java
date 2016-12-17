package com.cn21.invocation;

public class GreetFirstImpl implements GreetFirst{

	@Override
    public String getMessage( String name, String password ) {
	    // TODO Auto-generated method stub
		System.out.println("我是两个参数");
	    return (name+password);
    }


}
