package com.cn21.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author cbx try-with-resoource语句,可以不使用finally来释放资源
 */
public class TryWithResource {

	public void readFile( String fileName ) throws IOException {
		try (BufferedReader reader = new BufferedReader( new FileReader( fileName ) )) {
			StringBuilder builder = new StringBuilder();
			String line = null;
			while( (line = reader.readLine()) != null ) {
				builder.append( line );
				builder.append( "\t" );
			}
			System.out.println(builder.toString());
		}
	}

}
