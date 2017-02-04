package com.cn21.test;

class MySuperClass{
	//父类
	static{
		System.out.println("父类被初始化");
	}
	
	public static int value = 123;
	
	public static final String name = "name";
}

class MySubClass extends MySuperClass{
	//子类
	static{
		System.out.println("子类初始化");
	}
}

/**
 * @author cbx
 * 被动引用的例子
 * 子类引用父类的静态变量，子类不会被初始化
 * 常量定义时会进入常量池，调用时不会触发定义常量的类的初始化
 */
public class PassiveReference { 

	public static void main( String[] args ) {
		// TODO Auto-generated method stub
		System.out.println(MySubClass.value);
		System.out.println(MySubClass.name);
	}

}
