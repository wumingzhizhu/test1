package com.cn21.nio;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NioTest {

	public static void main( String[] args ) {
		// TODO Auto-generated method stub
		try {
			// loadWebPage( "http://www.baidu.com" );
			//copyFile( "D://src.txt", "D://test.txt" );
			//mapFile();
			listFiles();
		}
		catch( Exception e ) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/**
	 * @param url
	 * @throws Exception 获取网页的内容，写入到文件
	 */
	public static void loadWebPage( String url ) throws Exception {
		try (FileChannel destChannel = FileChannel.open( Paths.get( "D://test.txt" ), StandardOpenOption.WRITE,
		    StandardOpenOption.CREATE )) {
			InputStream input = new URL( url ).openStream();
			ReadableByteChannel srcChannel = Channels.newChannel( input );
			destChannel.transferFrom( srcChannel, 0, Integer.MAX_VALUE );
		}
	}

	/**
	 * @param source
	 * @param dest
	 * @throws Exception 文件的复制
	 */
	public static void copyFile( String source, String dest ) throws Exception {
		ByteBuffer buffer = ByteBuffer.allocateDirect( 32 * 1024 );
		try (FileChannel srcChannel = FileChannel.open( Paths.get( source ), StandardOpenOption.READ )) {
			FileChannel destChannel = FileChannel.open( Paths.get( dest ), StandardOpenOption.CREATE, StandardOpenOption.WRITE );
			while( srcChannel.read( buffer ) > 0 || buffer.position() != 0 ) {
				buffer.flip();
				destChannel.write( buffer );
				buffer.compact();
			}

		}
	}
	
	/**
	 * @throws Exception
	 * 内存映射到文件
	 */
	public static void mapFile() throws Exception{
		try(FileChannel channel = FileChannel.open( Paths.get( "D://test.txt" ), 
			StandardOpenOption.READ,StandardOpenOption.WRITE )){
			MappedByteBuffer mappedByteBuffer = channel.map( FileChannel.MapMode.READ_WRITE, 0, channel.size() );
			byte b = mappedByteBuffer.get( 5 );
			mappedByteBuffer.put( 7,b );
			mappedByteBuffer.force();
		}
	}
	
	/**
	 * @throws Exception
	 * 罗列文件路径
	 */
	public static void listFiles() throws Exception{
		Path path = Paths.get( "D:/workspace/HelloWorld/src/main/java" );
		try(DirectoryStream<Path> stream = Files.newDirectoryStream( path,"*.java" )){
			for( Path entryPath : stream ) {
				System.out.println(entryPath.toString());
            }
		}
	}

}
