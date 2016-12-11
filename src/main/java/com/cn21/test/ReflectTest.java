package com.cn21.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author cbx 反射的基本例子
 */
public class ReflectTest {
	
	static class Cat{
		public Cat( String  name){
			System.out.println("Cat:" + name);
		}
	}
	
	class Dog{
		public Dog( int count ){
			System.out.println("Dog:" + count);
		}
	}

	public static void main( String[] args ){
		try {
			getConstract();
			ReflectTest reflectTest = new ReflectTest();
			reflectTest.getConstractByTwo();
        }
		catch( Exception e ) {
	        // TODO: handle exception
			e.printStackTrace();
        }
	}

	/**
	 * @throws Exception
	 * 通过反射获取变长参数的构造函数
	 */
	public static void getConstract() throws Exception {
		Constructor<emoloyee> constructor = emoloyee.class.getDeclaredConstructor( String[].class );
		constructor.newInstance( (Object)new String[] { "A", "B" } );
	}
	
	/**
	 * @throws Exception
	 * 关于静态嵌套类和非静态嵌套类获取构造方法的不同
	 */
	public void getConstractByTwo() throws Exception{
		Constructor<Cat> cat = Cat.class.getDeclaredConstructor( String.class );
		cat.newInstance( "i am cat" );
		Constructor<Dog> dog = Dog.class.getDeclaredConstructor( ReflectTest.class,int.class );
		dog.newInstance( this,3 );
		
	}

}

class emoloyee {

	public emoloyee(String... args) {
		// TODO Auto-generated constructor stub
		for( String string : args ) {
	        System.out.println(string);
        }
	}

}
