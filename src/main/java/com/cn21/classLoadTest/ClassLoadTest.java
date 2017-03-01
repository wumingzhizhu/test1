package com.cn21.classLoadTest;

/**
 * @author cbx 类加载器实例
 */
public class ClassLoadTest {
	
	/**
	 * 查看类加载器的层次结构
	 */
	public void displayLoad() {
		ClassLoader current = getClass().getClassLoader();
		while( current != null ) {
			System.out.println( current.toString() );
			current = current.getParent();
		}
	}
	
	public void ClassForNameTestLoader(){
		try {
	        String name = "com.cn21.classLoadTest.ClassForNameTest";
	        //Class.forName方法会去加载对应的类的静态方法
	        Class<?> class1 = Class.forName( name );
	        //Classloader这种方式加载类不会去执行类的静态方法
	        ClassLoader loader = this.getClass().getClassLoader();
	        Class<?> class2 = loader.loadClass( name );
        }
        catch( Exception e ) {
	        // TODO: handle exception
        	e.printStackTrace();
        }
	}

	public static void main( String[] args ) {
		// TODO Auto-generated method stub
		ClassLoadTest test = new ClassLoadTest();
		//test.displayLoad();
		test.ClassForNameTestLoader();
		
	}


}
