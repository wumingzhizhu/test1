package com.cn21.test;

/**
 * VM Args: -Xss 128k
 * @author cbx
 * 
 *
 */
public class javaVMStackSOF {
	
	private int stackLength = 1;
	
	public void stackAdd(){
		stackLength ++;
		stackAdd();
	}

	public static void main( String[] args ) throws Throwable{
		// TODO Auto-generated method stub
		javaVMStackSOF oom = new javaVMStackSOF();
		try{
			oom.stackAdd();
		}
		catch(Throwable e){
			System.out.println("长度:" + oom.stackLength);
			e.printStackTrace();
		}

	}

}
