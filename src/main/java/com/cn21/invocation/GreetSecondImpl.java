package com.cn21.invocation;

public class GreetSecondImpl implements GreetSecond{

	@Override
    public String getMessage( String name ) {
	    // TODO Auto-generated method stub
		System.out.println("我是一个参数");
	    return name;
    }

}
