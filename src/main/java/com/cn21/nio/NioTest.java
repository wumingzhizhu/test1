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
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;

public class NioTest {

	public static void main( String[] args ) {
		// TODO Auto-generated method stub
		try {
			// loadWebPage( "http://www.baidu.com" );
			// copyFile( "D://src.txt", "D://test.txt" );
			// mapFile();
			// listFiles();
			// useNewFile();
			calculate();
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
	 * @throws Exception 内存映射到文件
	 */
	public static void mapFile() throws Exception {
		try (FileChannel channel = FileChannel
		        .open( Paths.get( "D://test.txt" ), StandardOpenOption.READ, StandardOpenOption.WRITE )) {
			MappedByteBuffer mappedByteBuffer = channel.map( FileChannel.MapMode.READ_WRITE, 0, channel.size() );
			byte b = mappedByteBuffer.get( 5 );
			mappedByteBuffer.put( 7, b );
			mappedByteBuffer.force();
		}
	}

	/**
	 * @throws Exception 罗列文件路径
	 */
	public static void listFiles() throws Exception {
		Path path = Paths.get( "D:/workspace/HelloWorld/src/main/java" );
		try (DirectoryStream<Path> stream = Files.newDirectoryStream( path, "*.java" )) {
			for( Path entryPath : stream ) {
				System.out.println( entryPath.toString() );
			}
		}
	}

	/**
	 * @throws Exception java7中新增的处理文件的类
	 */
	public static void useNewFile() throws Exception {
		Path path = Paths.get( "D:/123.xlsx" );
		// windosw dos
		DosFileAttributeView view = Files.getFileAttributeView( path, DosFileAttributeView.class );
		if( view != null ) {
			// 用readAttributes获取该接口的实现对象
			DosFileAttributes attributes = view.readAttributes();
			System.out.println( attributes.isReadOnly() );
			System.out.println( attributes.isDirectory() );
		}
		// 文件的基本属性
		BasicFileAttributeView basicView = Files.getFileAttributeView( path, BasicFileAttributeView.class );
		if( basicView != null ) {
			BasicFileAttributes basicAttributes = basicView.readAttributes();
			System.out.println( basicAttributes.lastModifiedTime() );
		}
		// 文件的所有者
		FileOwnerAttributeView ownerView = Files.getFileAttributeView( path, FileOwnerAttributeView.class );
		if( ownerView != null ) {
			System.out.println( ownerView.getOwner() );
		}

	}

	/**
	 * @throws Exception 监控目录变化
	 */
	public static void calculate() throws Exception {
		WatchService service = FileSystems.getDefault().newWatchService();
		Path path = Paths.get( "D:/" ).toAbsolutePath();
		path.register( service, StandardWatchEventKinds.ENTRY_CREATE );
		while( true ) {
			WatchKey key = service.take();
			// 获取所有的事件
			for( WatchEvent<?> event : key.pollEvents() ) {
				Path myPath = (Path)event.context();
				myPath = path.resolve( myPath );
				long size = Files.size( myPath );
				System.out.println( myPath + " --> " + size );
			}
			key.reset();
		}

	}

}
