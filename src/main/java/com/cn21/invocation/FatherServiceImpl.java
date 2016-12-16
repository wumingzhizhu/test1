package com.cn21.invocation;

public class FatherServiceImpl implements FatherService{

	@Override
    public void getName() {
	    // TODO Auto-generated method stub
	    System.out.println("我的名字是父亲");
    }

	@Override
    public void getAge() {
	    // TODO Auto-generated method stub
		System.out.println("我的年龄是50岁了");
	    
    }

}
