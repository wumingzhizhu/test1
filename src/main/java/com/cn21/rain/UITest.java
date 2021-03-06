package com.cn21.rain;

import java.awt.AWTEvent;
import java.awt.ActiveEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.SecondaryLoop;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.MessageFormat;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.LayerUI;

import org.omg.CORBA.portable.ApplicationException;

public class UITest {

	public static void main( String[] args ) {
		// TODO Auto-generated method stub
		try {
			//createShapeWindoes();
			//createTransparentWindows();
			//HighlightLayerUI highlightLayerUI = new HighlightLayerUI();
			//highlightLayerUI.useHightlight();
			//MyEvent myEvent = new MyEvent( "hello word" );
			//myEvent.useMyEvent( myEvent );
			UITest test = new UITest();
			//test.downloadFile();
			//test.useLoop();
			test.selectPlaf();
        }
        catch( Exception e ) {
        	e.printStackTrace();
        }

	}
	
	/**
	 * @throws Exception
	 * 圆形窗口
	 */
	public static void createShapeWindoes() throws Exception{
		Frame frame = new Frame();
		//去标题栏
		frame.setUndecorated( true );
		Shape shape = new Ellipse2D.Float( 0, 0, 400, 300 );
		frame.setShape( shape );
		Label label = new Label( "hello wrod" );
		frame.add(label);
		frame.setSize( 400,300 );
		frame.setVisible( true );
	}
	
	/**
	 * @throws Exception
	 * 半透明窗口
	 */
	public static void createTransparentWindows() throws Exception{
		final Frame frame = new Frame();
		frame.setUndecorated( true );
		frame.setSize( 400, 300 );
		final JSlider jSlider = new JSlider( 0, 100, 80 );
		jSlider.addChangeListener( new ChangeListener() {
			
			@Override
			public void stateChanged( ChangeEvent e ) {
				// TODO Auto-generated method stub
				frame.setOpacity( jSlider.getValue() / 100.0f);
			}
		} );
		frame.add( jSlider );
		frame.setOpacity( 0.8f );
		frame.setVisible( true );
	}
	
	/**
	 * 模拟更新下载速度
	 */
	public void downloadFile(){
		JFrame jFrame = new JFrame();
		final JProgressBar progressBar = new JProgressBar();
		jFrame.add( progressBar, BorderLayout.NORTH );
		JLabel label = new JLabel();
		jFrame.add( label,BorderLayout.CENTER );
		DownloadWorker worker = new DownloadWorker(label);
		worker.addPropertyChangeListener( new PropertyChangeListener() {
			
			@Override
			public void propertyChange( PropertyChangeEvent evt ) {
				// TODO Auto-generated method stub
				//progress方法是在事件分发线程中调用，利用上面publish方法的中间结果来更新界面
				if("progress".equals( evt.getPropertyName() )){
					progressBar.setValue( (int)evt.getNewValue() );
				}
			}
		} );
		//启动任务
		worker.execute();
		jFrame.setSize( 400, 300 );
		jFrame.setVisible( true );
	}
	
	/**
	 * SecondaryLoop类即可以阻塞当前线程以等待结束，又可以继续处理事件队列中的事件，并且不会造成界面失去响应
	 */
	public void useLoop(){
		final JFrame frame = new JFrame();
		frame.setSize( 300, 400 );
		final JLabel label = new JLabel( "名字" );
		frame.addMouseListener( new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				EventQueue queue = Toolkit.getDefaultToolkit().getSystemEventQueue();
				SecondaryLoop loop = queue.createSecondaryLoop();
				WorkerThread workerThread = new WorkerThread(loop );
				workerThread.start();
				//可以阻塞当前事件的执行，但是界面不会失去响应
				loop.enter();
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				label.setText( "mingiz" );
			}
			
			
		} );
		
		frame.add( label );
		frame.setVisible( true );
	}
	
	/**
	 * 切换swing界面的外观样式
	 */
	public void selectPlaf(){
		final JFrame frame = new JFrame();
		//获取java平台所有可用的样式
		UIManager.LookAndFeelInfo info[] = UIManager.getInstalledLookAndFeels();
		//下拉选择事件
		JComboBox combxBox = new JComboBox(info);
		combxBox.addItemListener( new ItemListener() {
			@Override
			public void itemStateChanged( ItemEvent e ) {
				// TODO Auto-generated method stub
				if(ItemEvent.SELECTED == e.getStateChange()){
					UIManager.LookAndFeelInfo info = (UIManager.LookAndFeelInfo)e.getItem();
					try{
						UIManager.setLookAndFeel( info.getClassName() );
						//通知界面上所有组件更新外观样式
						SwingUtilities.updateComponentTreeUI( frame );
					}
					catch(Exception ex){
						ex.printStackTrace();
					}
				}
				
			}
		} );
		frame.add( combxBox,BorderLayout.NORTH);
		frame.add( new JButton("按钮"),BorderLayout.SOUTH );
		frame.setSize( 300, 400 );
		frame.setVisible( true );
	}
	
}

/**
 * @author cbx
 * LayerUI的使用，高亮显示
 *
 */
class HighlightLayerUI extends LayerUI{
	@Override
	public void paint(Graphics g, JComponent c){
		super.paint( g, c );
		g.setColor( Color.RED );
		g.drawRect( 0, 0, c.getWidth()-1, c.getHeight()-1 );
	}
	
	public void useHightlight(){
		final JFrame frame = new JFrame();
		frame.setSize( 400,300 );
		frame.setLayout( new GridLayout(2,1) );
		//frame.add( new JLabel("标签") );
		JButton button = new JButton("按钮");
		final JLayer<? extends Component> layer = new JLayer<Component>( button );
		final LayerUI layerUI = new HighlightLayerUI();
		final LayerUI defaultuUI = new LayerUI();
		frame.add( layer );
		button.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				layer.setUI( layerUI );
			}
			
			@Override
		    public void mouseExited(MouseEvent e) {
				layer.setUI( defaultuUI );
			}
		} );
		frame.setVisible( true );
		
	}
	
}

/**
 * @author cbx
 * 自定义事件
 */
class MyEvent extends AWTEvent implements ActiveEvent{
	private String content;
	public MyEvent(String content){
		super( content, RESERVED_ID_MAX + 1);
		this.content = content;
	}
	@Override
    public void dispatch() {
		System.out.println("字符串的长度为: " + content.length());
    }
	
	public void useMyEvent(MyEvent myEvent){
		Frame frame = new Frame();
		frame.setVisible( true );
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		EventQueue queue = toolkit.getSystemEventQueue();
		queue.postEvent( myEvent );
	}
	
}

/**
 * @author cbx
 * 模拟更新下载速度
 * StringWorkder<T,V>
 * T - 此 SwingWorker 的 doInBackground 和 get 方法返回的结果类型
 * V - 用于保存此 SwingWorker 的 publish 和 process 方法的中间结果的类型
 */
class DownloadWorker extends SwingWorker<String, Double>{
	
	private JLabel label;
	
	public DownloadWorker(JLabel label) {
	    // TODO Auto-generated constructor stub
		this.label = label;
    }

	@Override
    protected String doInBackground() throws Exception {
	    // TODO Auto-generated method stub
		Random random = new Random();
		for(int i=0;i < 100;i ++){
			Thread.sleep( random.nextInt( 1000 ) );
			//更新进度，从1到100
			setProgress( i + 1 );
			//在工作线程调用，用来发布中间结果
			publish( random.nextDouble() * 30 );
		}
		//似乎是随便返回一个字符串，没有用到
	    //return "<Path>";
		return "1";
    }
	
	@Override
	protected void process(List<Double> chunk){
		//选择最后一个结果来显示
		Double speed = chunk.get( chunk.size() - 1 );
		label.setText( MessageFormat.format( "下载速度 :{0,number,#.##}", speed ) );
	}
	
}

/**
 * @author cbx
 * SecondaryLoop类的使用
 */
class WorkerThread extends Thread{
	
	private SecondaryLoop loop;
	
	public WorkerThread(SecondaryLoop loop){
		this.loop = loop;
	}
	
	@Override
	public void run(){
		try {
	        Thread.sleep( 5000 );
	        System.out.println("1111111111");
        }
        catch( Exception e ) {
	        // TODO: handle exception
        	e.printStackTrace();
        }
		//恢复被enter阻塞的事件
		loop.exit();
	}
	
}

