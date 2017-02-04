package com.cn21.invocation;

public class UserServiceImpl implements UserService{

	@Override
    public void getName() {
	    // TODO Auto-generated method stub
	    System.out.println("我的名字是:电信客户");
    }

	@Override
    public void getPassword() {
	    // TODO Auto-generated method stub
	    System.out.println("我的密码是:10000");
    }

}
