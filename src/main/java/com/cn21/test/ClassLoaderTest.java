package com.cn21.test;

import java.io.InputStream;

/**
 * @author cbx 不同类加载器对Instance关键字的影响
 */
public class ClassLoaderTest {

	public static void main( String[] args ) throws Exception {
		// TODO Auto-generated method stub
		ClassLoader loader = new ClassLoader() {
			@Override
			public Class<?> loadClass( String name ) throws ClassNotFoundException {
				try {
					String fileName = name.substring( name.lastIndexOf( "." ) + 1 ) + ".class";
					InputStream in = getClass().getResourceAsStream( fileName );
					if( in == null ) {
						return super.loadClass( name );
					}
					byte[] b = new byte[in.available()];
					in.read( b );
					return defineClass( name, b, 0, b.length );
				}
				catch( Exception e ) {
					// TODO: handle exception
					throw new ClassNotFoundException( name );
				}
			}
		};
		//自己实现的类加载器
		Object object = loader.loadClass( "com.cn21.test.ClassLoaderTest" ).newInstance();
		System.out.println( object.getClass() );
		//只要类加载器不同，两个类就不同
		System.out.println( object instanceof com.cn21.test.ClassLoaderTest );

	}

}
