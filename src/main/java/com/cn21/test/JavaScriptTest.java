package com.cn21.test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JavaScriptTest {

	public static void main( String[] args ) {
		// TODO Auto-generated method stub
		try {
	        gree();
        }
        catch( Exception e ) {
	        // TODO: handle exception
        	e.printStackTrace();
        }
	}
	
	public static void gree() throws Exception{
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName( "JavaScript" );
		if(engine == null){
			throw new RuntimeException( "找不到javascript引擎" );
		}
		engine.eval( "println('hello')" );
	}

}
