package com.cn21.Internation;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

/**
 * @author cbx 国际化
 */
public class Internation {

	public static void main( String[] args ) {
		// TODO Auto-generated method stub
		try {
			filter( "你好，123世界! " );
		}
		catch( Exception e ) {
			e.printStackTrace();
		}

	}

	/**
	 * @throws Exception 编解码过程中的字符串过滤
	 */
	public static void filter( String str ) throws Exception {
		Charset charset = StandardCharsets.ISO_8859_1;
		CharsetDecoder decoder = charset.newDecoder();
		CharsetEncoder encoder = charset.newEncoder();
		// 对于错误输入和不可映射的字符错误的默认操作是报告它们。可通过 onMalformedInput
		// 方法更改针对错误输入的错误操作；可通过 onUnmappableCharacter 方法更改不可映射的字符错误的操作
		encoder.onUnmappableCharacter( CodingErrorAction.IGNORE );
		CharBuffer buffer = CharBuffer.wrap( str );
		ByteBuffer byteBuffer = encoder.encode( buffer );
		CharBuffer result = decoder.decode( byteBuffer );
		System.out.println( result.toString() );

	}

}
