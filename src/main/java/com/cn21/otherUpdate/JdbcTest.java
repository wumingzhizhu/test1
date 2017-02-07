package com.cn21.otherUpdate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author cbx
 * java7中数据库的操作
 */
public class JdbcTest {

	public static final String URL = "jdbc:mysql://127.0.0.1/map";

	public static final String DRIVER = "com.mysql.jdbc.Driver";

	public static final String USER = "root";

	public static final String PASSWORD = "root";

	public static void main( String[] args ) {
		// TODO Auto-generated method stub
		try {
			JdbcTest test = new JdbcTest();
			test.abortConnection();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}

	}

	/**
	 * @throws Exception abort强制关闭数据库链接
	 */
	public void abortConnection() throws Exception {
		Class.forName( DRIVER );
		Connection connection = DriverManager.getConnection( URL, USER, PASSWORD );
		ThreadPoolExecutor executor = new DebugExecutorService( 2, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>() );
		connection.abort( executor );
		executor.shutdown();
		try {
			executor.awaitTermination( 5, TimeUnit.MINUTES );
		}
		catch( Exception e ) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}

class DebugExecutorService extends ThreadPoolExecutor {

	public DebugExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
	        BlockingQueue<Runnable> workQueue) {
		super( corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue );
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void beforeExecute( Thread t, Runnable r ) {
		System.out.println( "清理任务: " + r.getClass() );
		super.beforeExecute( t, r );
	}

}
